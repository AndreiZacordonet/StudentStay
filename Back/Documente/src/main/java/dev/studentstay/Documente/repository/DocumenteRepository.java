package dev.studentstay.Documente.repository;

import dev.studentstay.Documente.model.Documente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumenteRepository extends JpaRepository<Documente, Long> , JpaSpecificationExecutor<Documente> {
    Documente searchDocumenteByIdStudent(Long idStudent);
}
