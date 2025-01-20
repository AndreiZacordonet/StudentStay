package ProiectPAWCamine.Repository;

import ProiectPAWCamine.Entity.CamineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CamineRepository extends JpaRepository<CamineEntity, Long> {
    Optional<CamineEntity> findByNume(String nume);
}