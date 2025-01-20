package ProiectPAWCamine.Controller;

import ProiectPAWCamine.DTOs.CamineDTO;
import ProiectPAWCamine.Entity.CamineEntity;
import ProiectPAWCamine.Handlers.UnprocessableEntityException;
import ProiectPAWCamine.Service.CamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CamineController {

    @Autowired
    private CamineService camineService;

    @GetMapping("api/student-stay/camine")
    public ResponseEntity<List<CamineEntity>> getAllCamine(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "items", required = false, defaultValue = "100") int itemsPerPage,
                                                           @RequestParam(name = "nume", required = false) String nume) {
        if (nume != null) {
            List<CamineEntity> result = camineService.findByName(nume).stream().toList();
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.ok(camineService.findAll(page, itemsPerPage));
        }
    }

    @GetMapping("api/student-stay/camine/{id}")
    public ResponseEntity<CamineEntity> getCaminById(@PathVariable Long id) {

        Optional<CamineEntity> camin = camineService.findById(id);
        return camin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("api/student-stay/camine")
    public ResponseEntity<?> createCamin(@RequestBody CamineDTO camin) {
        try {
            if (camin == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cererea nu poate fi null!");
            }

            CamineEntity camineEntity = new CamineEntity();

            if (camin.getNume().length() < 2 || !camineService.findByName(camin.getNume()).isEmpty()) {
                throw new UnprocessableEntityException("Numele nu este valid sau exista deja un camin cu acest nume!");
            }
            camineEntity.setNume(camin.getNume());

            if (camin.getLocatie().length() < 4) {
                throw new UnprocessableEntityException("Locatia nu este valida!");
            }
            camineEntity.setLocatie(camin.getLocatie());

            if (camin.getEtaje() == 0) {
                throw new UnprocessableEntityException("Numar etaje nu poate fi null!");
            }
            camineEntity.setEtaje(camin.getEtaje());

            if (camin.getCamere() == 0) {
                throw new UnprocessableEntityException("Numar camere nu poate fi null!");
            }
            camineEntity.setCamere(camin.getCamere());

            if (camin.getListaCamereEtaj() == null) {
                throw new UnprocessableEntityException("Lista camere pe etaje nu poate fi null!");
            }
            camineEntity.setListaCamereEtaj(camin.getListaCamereEtaj());

            if (camin.getLocuri() == 0) {
                throw new UnprocessableEntityException("Numarul de locuri nu poate fi null!");
            }
            camineEntity.setLocuri(camin.getLocuri());

            if (camin.getDocumentCamin() == null) {
                throw new UnprocessableEntityException("Calea catre document nu poate fi nula!");
            }
            camineEntity.setDocumentCamin(camin.getDocumentCamin());

            if (camin.getCaleFolderOglinzi() == null) {
                throw new UnprocessableEntityException("Calea catre oglinzi nu poate fi nula!");
            }
            camineEntity.setCaleFolderOglinzi(camin.getCaleFolderOglinzi());

            if (camin.getDescriere() == null) {
                throw new UnprocessableEntityException("Descrierea nu poate fi nula!");
            }
            camineEntity.setDescriere(camin.getDescriere());

            return ResponseEntity.status(HttpStatus.CREATED).body(camineService.save(camineEntity));

        } catch (UnprocessableEntityException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare interna a serverului!");
        }
    }

    @PatchMapping("api/student-stay/camine/{id}")
    public ResponseEntity<?> updateCamin(@PathVariable Long id, @RequestBody CamineDTO caminDetails) {
        Optional<CamineEntity> optionalCamin = camineService.findById(id);

        if (optionalCamin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Căminul nu a fost găsit!");
        }

        try {
            CamineEntity existingCamin = optionalCamin.get();

            if (caminDetails.getNume() != null) {
                existingCamin.setNume(caminDetails.getNume());
            }
            if (caminDetails.getLocatie() != null) {
                existingCamin.setLocatie(caminDetails.getLocatie());
            }
            if (caminDetails.getEtaje() != null) {
                existingCamin.setEtaje(caminDetails.getEtaje());
            }
            if (caminDetails.getCamere() != null) {
                existingCamin.setCamere(caminDetails.getCamere());
            }
            if (caminDetails.getListaCamereEtaj() != null) {
                existingCamin.setListaCamereEtaj(caminDetails.getListaCamereEtaj());
            }
            if (caminDetails.getDocumentCamin() != null) {
                existingCamin.setDocumentCamin(caminDetails.getDocumentCamin());
            }
            if (caminDetails.getCaleFolderOglinzi() != null) {
                existingCamin.setCaleFolderOglinzi(caminDetails.getCaleFolderOglinzi());
            }
            if (caminDetails.getDescriere() != null) {
                existingCamin.setDescriere(caminDetails.getDescriere());
            }
            if (caminDetails.getLocuri() != null) {
                existingCamin.setLocuri(caminDetails.getLocuri());
            }

            CamineEntity updatedCamin = camineService.save(existingCamin);

            return ResponseEntity.ok(updatedCamin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare internă a serverului!");
        }
    }

    @DeleteMapping("api/student-stay/camine/{id}")
    public ResponseEntity<?> deleteCamin(@PathVariable Long id) {
        Optional<CamineEntity> camin = camineService.findById(id);

        if (camin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caminul nu a fost gasit!");
        }

        try {
            camineService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare interna a serverului!");
        }
    }
}