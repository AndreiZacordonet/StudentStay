package dev.studentstay.Documente.repository;

import dev.studentstay.Documente.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RezervareRepository extends JpaRepository<Rezervare, Long> {
    Optional<Rezervare> findDistinctByEmail(String email);
}
