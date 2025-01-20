package ProiectPAWCamine.Service;


import ProiectPAWCamine.Entity.CamineEntity;
import ProiectPAWCamine.Repository.CamineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamineService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CamineRepository camineRepository;


    public List<CamineEntity> findAll(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        return camineRepository.findAll(pageable).getContent();
    }

    public Optional<CamineEntity> findById(Long id) {
        return camineRepository.findById(id);
    }

    public Optional<CamineEntity> findByName(String nume ) {
        return camineRepository.findByNume(nume);
    }


    public CamineEntity save(CamineEntity camin) {
        return camineRepository.save(camin);
    }

    public void deleteById(Long id ) {
        camineRepository.deleteById(id);
    }

}