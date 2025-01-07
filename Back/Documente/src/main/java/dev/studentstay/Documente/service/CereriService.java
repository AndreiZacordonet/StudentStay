package dev.studentstay.Documente.service;

import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.repository.CereriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;


@Service
public class CereriService {

    private final CereriRepository cerereRepository;
    private final StudentServiceClient studentServiceClient;

    @Autowired
    public CereriService(CereriRepository cerereRepository, CereriRepository cereriRepository, StudentServiceClient studentServiceClient) {
        this.cerereRepository = cerereRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public List<CereriEntity> getAllCereri(int page, int itemsPerPage, CereriEntity.TipCerere tip) {
        if(page!=-1 && itemsPerPage!=0 ) {
            Pageable pageable = PageRequest.of(page, itemsPerPage);
            if(tip==null)
                return cerereRepository.findAll(pageable).getContent();
            else
                return cerereRepository.findAllByTipCerere(pageable,tip).getContent();
        }else
        {
            if(tip==null)
                return cerereRepository.findAll();
            else
                return cerereRepository.findAllByTipCerere(tip);
        }
    }

    public Optional<CereriEntity> getCerereById(Long id) {
        return cerereRepository.findById(id);
    }

    public CereriEntity getCerereByUserId(Long userId, String token, String userRole) {
        if (!studentServiceClient.checkIfStudentExists(userId, token, userRole)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Student ID does not exist");
        }
        return cerereRepository.findByUserId(userId);
    }

    public CereriEntity addCerere(CereriEntity cerere, String token, String userRole) {
        CereriEntity existingCerere = cerereRepository.findByUserId(cerere.getUserId());
        if (existingCerere!=null) {
            throw new IllegalArgumentException("Exista deja o cerere pentru acest utilizator.");
        }

        if (!studentServiceClient.checkIfStudentExists(cerere.getUserId(), token, userRole)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Student ID does not exist");
        }
        return cerereRepository.save(cerere);
    }

    public ResponseEntity<CereriEntity> updateCerere(Long id, CereriEntity cerere) {
        Optional<CereriEntity> cerereE=cerereRepository.findById(id);
        if(cerereE == null)
        {
           return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(cerereRepository.save(cerere));
        }

    }

    public ResponseEntity<CereriEntity> deleteCerere(Long idCerere) {
        CereriEntity cerere = cerereRepository.findById(idCerere).orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Cererea nu exista"));

        cerereRepository.deleteById(idCerere);

        return ResponseEntity.ok(cerere);
    }
}
