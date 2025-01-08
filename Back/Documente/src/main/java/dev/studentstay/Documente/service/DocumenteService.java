package dev.studentstay.Documente.service;

import dev.studentstay.Documente.repository.CereriRepository;
import dev.studentstay.Documente.repository.DocumenteRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumenteService {

    private final DocumenteRepository documenteRepository;
    private final CereriRepository cereriRepository;
    private final StudentServiceClient studentServiceClient;

    public DocumenteService(DocumenteRepository documenteRepository, CereriRepository cereriRepository, StudentServiceClient studentServiceClient) {
        this.documenteRepository = documenteRepository;
        this.cereriRepository = cereriRepository;
        this.studentServiceClient = studentServiceClient;
    }

//    public getDocumente()
}
