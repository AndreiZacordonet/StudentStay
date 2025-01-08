package dev.studentstay.Documente.service;

import dev.studentstay.Documente.dto.DocumentDto;
import dev.studentstay.Documente.exceptions.DocumentFaraCerereException;
import dev.studentstay.Documente.exceptions.DocumentNotFoundException;
import dev.studentstay.Documente.model.Acte;
import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.model.Documente;
import dev.studentstay.Documente.repository.CereriRepository;
import dev.studentstay.Documente.repository.DocumenteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class DocumenteService {

    private final DocumenteRepository documenteRepository;
    private final CereriRepository cereriRepository;
    private final StudentServiceClient studentServiceClient;

    @Value("${file.system.path}")
    private String fileSystemPath;

    public DocumenteService(DocumenteRepository documenteRepository, CereriRepository cereriRepository, StudentServiceClient studentServiceClient) {
        this.documenteRepository = documenteRepository;
        this.cereriRepository = cereriRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public Page<Documente> getDocumente(Pageable pageable, String authorization, String userRole,
                                        String cnpStudent, Acte act) {

        System.out.println("\n\ncnp: " + cnpStudent + "\n\n");

        return documenteRepository.findAll(Specification.where(
                studentIdEquals(cnpStudent != null ? studentServiceClient.getStudentIdByCnp(cnpStudent, authorization, userRole)
                        : null)
                        .and(acteEquals(act))
        ), pageable);
    }

    public static Specification<Documente> studentIdEquals(Long idStudent) {
        return ((root, query, criteriaBuilder) ->
                idStudent == null ? null : criteriaBuilder.equal(root.get("idStudent"), idStudent));
    }

    public static Specification<Documente> acteEquals(Acte act) {
        return ((root, query, criteriaBuilder) ->
                act == null ? null : criteriaBuilder.equal(root.get("acte"), act));
    }

    public Documente getById(Long id) {
        return documenteRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Documentul cu id '" + id + "' nu a fost gasit."));
    }

    public Documente createNew(DocumentDto newDoc, MultipartFile file) throws IOException {
        if (cereriRepository.findByUserId(newDoc.getIdStudent()) == null) {
            throw new DocumentFaraCerereException("Studentul cu id '" + newDoc.getIdStudent() + "' nu a trimis cerere.");
        }

        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            throw new IllegalArgumentException("Invalid file. Only non-empty .pdf files are accepted.");
        }

        String filePath = "src/main/resources/files" + "/" + newDoc.getIdStudent() + "_" + newDoc.getDocumentName();
        Path destinationPath = Paths.get(filePath);

        Files.createDirectories(destinationPath.getParent());

        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        Documente doc = new Documente(null, newDoc.getIdStudent(), filePath, null, null);

        documenteRepository.save(doc);

        return doc;
    }
}
