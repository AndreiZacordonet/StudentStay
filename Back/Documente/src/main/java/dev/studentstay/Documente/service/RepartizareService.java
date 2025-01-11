package dev.studentstay.Documente.service;

import dev.studentstay.Documente.exceptions.RepartizareNodFoundException;
import dev.studentstay.Documente.model.*;
import dev.studentstay.Documente.repository.ClasamentRepository;
import dev.studentstay.Documente.repository.RepartizareRepository;
import dev.studentstay.Documente.repository.RezervareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepartizareService {

    private final RepartizareRepository repartizareRepository;
    private final StudentServiceClient studentServiceClient;
    private final ClasamentRepository clasamentRepository;
    private final RezervareRepository rezervareRepository;

    public void populate() {
        List<Clasament> clasamentList = clasamentRepository.findAll(Sort.by(Sort.Direction.ASC, "pozitie"));

        Map<String, Rezervare> rezervareMap = rezervareRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Rezervare::getEmail, rez -> rez));

        Map<CoduriCamine, List<String>> availableRooms = new HashMap<>();
        RoomNumbers.CAMIN_ROOMS.forEach((camin, rooms) -> availableRooms.put(camin, new ArrayList<>(rooms)));

        List<Repartizare> repartizari = new ArrayList<>();

        for (Clasament student : clasamentList) {
            String email = student.getEmail();
            Rezervare rezervare = rezervareMap.get(email);

            boolean assigned = false;
            if (rezervare != null) {
                List<Map<CoduriCamine, List<String>>> preferences = rezervare.getCamere();

                for (Map<CoduriCamine, List<String>> preference : preferences) {
                    for (Map.Entry<CoduriCamine, List<String>> entry : preference.entrySet()) {
                        CoduriCamine camin = entry.getKey();
                        List<String> preferredRooms = entry.getValue();

                        for (String room : preferredRooms) {
                            if (availableRooms.get(camin).contains(room)) {
                                repartizari.add(new Repartizare(null, email, camin, room));

                                availableRooms.get(camin).remove(room);
                                assigned = true;
                                break;
                            }
                        }
                        if (assigned) break;
                    }
                    if (assigned) break;
                }
            }

            if (!assigned) {
                for (Map.Entry<CoduriCamine, List<String>> entry : availableRooms.entrySet()) {
                    CoduriCamine camin = entry.getKey();
                    List<String> rooms = entry.getValue();

                    if (!rooms.isEmpty()) {
                        String room = rooms.get(0); // Take the first available room
                        repartizari.add(new Repartizare(null, email, camin, room));
                        rooms.remove(room);
                        assigned = true;
                        break;
                    }
                }
            }

            if (!assigned) {
                throw new IllegalStateException("No available rooms for student: " + email);
            }
        }
        repartizareRepository.deleteAll();
        repartizareRepository.saveAll(repartizari);
    }

    public void editByEmail(String email, CoduriCamine camin, String room) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }

        Repartizare existingRepartizare = repartizareRepository.findDistinctByEmail(email);
        if (existingRepartizare == null) {
            throw new RepartizareNodFoundException("Repartizare not found for email: " + email);
        }

        if (!RoomNumbers.CAMIN_ROOMS.containsKey(camin)) {
            throw new IllegalArgumentException("Invalid camin: " + camin);
        }

        List<String> roomsForCamin = RoomNumbers.CAMIN_ROOMS.get(camin);
        if (!roomsForCamin.contains(room)) {
            throw new IllegalArgumentException("Invalid room: " + room + " for camin: " + camin);
        }

        boolean isRoomOccupied = repartizareRepository.existsByCaminAndCamera(camin, room);
        if (isRoomOccupied) {
            throw new IllegalArgumentException("Room " + room + " in camin " + camin + " is already occupied.");
        }

        existingRepartizare.setCamin(camin);
        existingRepartizare.setCamera(room);

        repartizareRepository.save(existingRepartizare);
    }
}
