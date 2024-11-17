package ProiectPAWCereri.Service;

import ProiectPAWCereri.Entity.CereriEntity;
import ProiectPAWCereri.Repository.CereriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CereriService {

    private final CereriRepository cerereRepository;

    @Autowired
    public CereriService(CereriRepository cerereRepository) {
        this.cerereRepository = cerereRepository;
    }

    public List<CereriEntity> getAllCereri(int page, int itemsPerPage,CereriEntity.TipCerere tip) {
        if(page!=-1 && itemsPerPage!=0 ) {
            Pageable pageable = PageRequest.of(page, itemsPerPage);
            if(tip==null)
                return cerereRepository.findAll(pageable).getContent();
            else
                return cerereRepository.findAllByTipCerere(pageable,tip).getContent();
        }else
        {
            if(tip==null)
                return cerereRepository.findAll();
            else
                return cerereRepository.findAllByTipCerere(tip);
        }
    }

    public Optional<CereriEntity> getCerereById(Long id) {
        return cerereRepository.findById(id);
    }

    public CereriEntity getCerereByUserId(Long userId) {
        return cerereRepository.findByUserId(userId);
    }

    public CereriEntity addCerere(CereriEntity cerere) {
        CereriEntity existingCerere = cerereRepository.findByUserId(cerere.getUserId());
        if (existingCerere!=null) {
            throw new IllegalArgumentException("Exista deja o cerere pentru acest utilizator.");
        }

        return cerereRepository.save(cerere);
    }

    public ResponseEntity<CereriEntity> updateCerere(Long id, CereriEntity cerere) {
        Optional<CereriEntity> cerereE=cerereRepository.findById(id);
        if(cerereE==null)
        {
           return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(cerereRepository.save(cerere));
        }

    }
}
