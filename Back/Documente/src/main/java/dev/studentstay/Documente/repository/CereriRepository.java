package dev.studentstay.Documente.repository;

import dev.studentstay.Documente.model.CereriEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CereriRepository extends JpaRepository<CereriEntity, Long> {
    CereriEntity findByUserId(Long userId);

    Page<CereriEntity> findAllByTipCerere(Pageable pageable, CereriEntity.TipCerere tip);
    List<CereriEntity> findAllByTipCerere(CereriEntity.TipCerere tip);
}
