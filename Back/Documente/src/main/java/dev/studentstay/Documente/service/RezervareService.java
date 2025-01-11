package dev.studentstay.Documente.service;

import dev.studentstay.Documente.dto.RezervareDto;
import dev.studentstay.Documente.exceptions.EmailNotFoundException;
import dev.studentstay.Documente.model.Rezervare;
import dev.studentstay.Documente.repository.RezervareRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RezervareService {

    private final RezervareRepository rezervareRepository;
    private final StudentServiceClient studentServiceClient;

    public RezervareService(RezervareRepository rezervareRepository, StudentServiceClient studentServiceClient) {
        this.rezervareRepository = rezervareRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public Rezervare create(RezervareDto newRezervare) {
        // TODO: verify each student email if exists
        // TODO: verify room number?

        Optional<Rezervare> existingRezervareOpt = rezervareRepository.findDistinctByEmail(newRezervare.getEmail());

        // Verify the primary student email
        if (studentServiceClient.getStudentByEmail(newRezervare.getEmail()) == null) {
            throw new EmailNotFoundException("The primary student email does not exist: " + newRezervare.getEmail());
        }

        // Verify emails in colegiCamera
        if (newRezervare.getColegiCamera() != null) {
            for (String colegEmail : newRezervare.getColegiCamera()) {
                if (studentServiceClient.getStudentByEmail(colegEmail) == null) {
                    throw new EmailNotFoundException("One of the roommate emails does not exist: " + colegEmail);
                }
            }
        }

        // TODO: Add logic to verify room numbers if necessary

        Rezervare rezervare;

        if (existingRezervareOpt.isPresent()) {
            // Update the existing reservation
            rezervare = existingRezervareOpt.get();
            rezervare.setCamere(newRezervare.getCamere());
            rezervare.setColegiCamera(newRezervare.getColegiCamera());
        } else {
            // Create a new reservation
            rezervare = new Rezervare();
            rezervare.setEmail(newRezervare.getEmail());
            rezervare.setCamere(newRezervare.getCamere());
            rezervare.setColegiCamera(newRezervare.getColegiCamera());
        }

        // Save and return the Rezervare entity
        return rezervareRepository.save(rezervare);
    }
}
