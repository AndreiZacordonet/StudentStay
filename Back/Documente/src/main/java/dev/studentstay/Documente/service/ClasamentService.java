package dev.studentstay.Documente.service;

import dev.studentstay.Documente.dto.StudentDto;
import dev.studentstay.Documente.exceptions.ClasamentEntryNotFoundExeception;
import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.model.Clasament;
import dev.studentstay.Documente.repository.ClasamentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClasamentService {

    private final ClasamentRepository clasamentRepository;
    public final StudentServiceClient studentServiceClient;

    public ClasamentService(ClasamentRepository clasamentRepository, StudentServiceClient studentServiceClient) {
        this.clasamentRepository = clasamentRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public void populate(String authToken) {
// Retrieve all students from the Student service
        List<StudentDto> allStudents = studentServiceClient.getAllStudents(authToken, "userRole");

        // Clear existing Clasament data to avoid duplicates
        clasamentRepository.deleteAll();

        // Transform StudentDTOs into Clasament entities and set default values
        List<Clasament> clasamentList = allStudents.stream()
                .map(student -> {
                    Clasament clasament = new Clasament();
                    clasament.setPozitie(0); // Default value
                    clasament.setPunctaj(student.getMedie() + 10 / student.getAn()); // TODO: adjust the formula
                    clasament.setEmail(student.getEmail());
                    return clasament;
                })
                .collect(Collectors.toList());

        Map<String, String> emailToNumeMap = allStudents.stream()
                .collect(Collectors.toMap(StudentDto::getEmail, StudentDto::getNume));

        clasamentList.sort(Comparator
                .comparing(Clasament::getPunctaj, Comparator.reverseOrder())
                .thenComparing(clasament -> emailToNumeMap.get(clasament.getEmail())));

        for (int i = 0; i < clasamentList.size(); i++) {
            clasamentList.get(i).setPozitie(i + 1);
        }

        // Save all Clasament entries to the database
        clasamentRepository.saveAll(clasamentList);
    }

    public Clasament modifyOne(Long id, Double punctaj, String authToken) {
        Clasament oneEntry = clasamentRepository.findById(id).orElseThrow(() -> new ClasamentEntryNotFoundExeception("Intrarea cu id '" + id + "' nu a fost gasita in clasament"));

        oneEntry.setPunctaj(punctaj);

        clasamentRepository.save(oneEntry);

        // TODO: recalculates clasament
        List<StudentDto> allStudents = studentServiceClient.getAllStudents(authToken, "userRole");
        List<Clasament> clasamentList = clasamentRepository.findAll();

        Map<String, String> emailToNumeMap = allStudents.stream()
                .collect(Collectors.toMap(StudentDto::getEmail, StudentDto::getNume));

        clasamentList.sort(Comparator
                .comparing(Clasament::getPunctaj, Comparator.reverseOrder())
                .thenComparing(clasament -> emailToNumeMap.get(clasament.getEmail())));

        for (int i = 0; i < clasamentList.size(); i++) {
            clasamentList.get(i).setPozitie(i + 1);
        }

        clasamentRepository.saveAll(clasamentList);

        return clasamentRepository.findById(id).orElseThrow(() -> new ClasamentEntryNotFoundExeception("Intrarea cu id '" + id + "' nu a fost gasita in clasament"));
    }

    public List<Clasament> getAll(int page, int itemsPerPage, String email) {
        if(page != -1 && itemsPerPage != 0 ) {
            Pageable pageable = PageRequest.of(page, itemsPerPage);
            if(email == null)
                return clasamentRepository.findAll(pageable).getContent();
            else
                return clasamentRepository.findFirstByEmail(email);
        }else
        {
            if(email == null)
                return clasamentRepository.findAll();
            else
                return clasamentRepository.findFirstByEmail(email);
        }
    }
}
