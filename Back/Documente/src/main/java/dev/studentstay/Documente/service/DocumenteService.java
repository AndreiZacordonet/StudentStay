package dev.studentstay.Documente.service;

import dev.studentstay.Documente.model.Acte;
import dev.studentstay.Documente.model.Documente;
import dev.studentstay.Documente.repository.CereriRepository;
import dev.studentstay.Documente.repository.DocumenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
}
