package ProiectPAWCereri.Controller;

import ProiectPAWCereri.Entity.CereriEntity;
import ProiectPAWCereri.Service.CereriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CereriController {

    @Autowired
    private CereriService cereriService;

    @GetMapping("/api/student-stay/cereri")
    public ResponseEntity<?> getAllCereri(@RequestParam(name = "page", required = false, defaultValue = "-1") int page,
                                           @RequestParam(name = "items", required = false, defaultValue = "0") int itemsPerPage,
                                           @RequestParam(name = "tip", required = false) String tip) {

        int ok = 1;
        if (tip != null) {
            for (CereriEntity.TipCerere constant : CereriEntity.TipCerere.values()) {
                if (constant.name().equals(tip.toString()))
                    ok = 0;
            }
        }

        if (ok == 1)
            return ResponseEntity.badRequest().body("Acest tip de cerere nu exista");

        CereriEntity.TipCerere tipCerere= CereriEntity.TipCerere.valueOf(tip);
        return ResponseEntity.ok(cereriService.getAllCereri(page, itemsPerPage, tipCerere));
    }

    @GetMapping("/api/student-stay/cereri/{id}")
    public Optional<CereriEntity> getCerereById(@PathVariable Long id) {
        return cereriService.getCerereById(id);
    }

    @GetMapping("/api/student-stay/cerere/user/{id}")
    public Optional<CereriEntity> getCerereByUserId(@PathVariable Long id) {
        return Optional.ofNullable(cereriService.getCerereByUserId(id));
    }

    @PostMapping("/api/student-stay/cereri")
    public CereriEntity addCerere(@RequestBody CereriEntity cerere) {
        return cereriService.addCerere(cerere);
    }

    @PutMapping("/api/student-stay/cereri/{id}")
    public ResponseEntity<CereriEntity> updateCerere(@PathVariable Long id, @RequestParam CereriEntity.TipCerere tipCerere) {
        Optional<CereriEntity> cerere = cereriService.getCerereById(id);
        if (cerere.isPresent()) {
            cerere.get().setTipCerere(tipCerere);
            return cereriService.updateCerere(id, cerere.get());
        } else
            return ResponseEntity.notFound().build();
    }
}
