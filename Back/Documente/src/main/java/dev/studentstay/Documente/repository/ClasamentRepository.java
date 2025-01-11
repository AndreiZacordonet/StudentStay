package dev.studentstay.Documente.repository;

import dev.studentstay.Documente.model.Clasament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasamentRepository extends JpaRepository<Clasament, Long> {
    List<Clasament> findFirstByEmail(String email);
}
