package ProiectPAWCereri.Repository;

import ProiectPAWCereri.Entity.CereriEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CereriRepository extends JpaRepository<CereriEntity, Long> {
    CereriEntity findByUserId(Long userId);

    Page<CereriEntity> findAllByTipCerere(Pageable pageable, CereriEntity.TipCerere tip);
    List<CereriEntity> findAllByTipCerere(CereriEntity.TipCerere tip);
}
