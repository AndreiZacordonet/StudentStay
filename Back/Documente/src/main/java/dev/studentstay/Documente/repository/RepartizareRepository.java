package dev.studentstay.Documente.repository;

import dev.studentstay.Documente.model.CoduriCamine;
import dev.studentstay.Documente.model.Repartizare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepartizareRepository extends JpaRepository<Repartizare, Long> {
    boolean existsByCaminAndCamera(CoduriCamine camin, String room);

    Repartizare findDistinctByEmail(String email);
}
