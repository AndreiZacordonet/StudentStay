package dev.studentstay.Documente.service;

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

            if (rezervare != null) {
                List<Map<CoduriCamine, List<String>>> preferences = rezervare.getCamere();

                boolean assigned = false;
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
        }

        repartizareRepository.saveAll(repartizari);
    }
}
