package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.service.CereriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.Optional;

@RestController
public class CereriController {

    @Autowired
    private CereriService cereriService;

    @GetMapping("/api/student-stay/cereri")
    @Parameter(name = "page")
    @Parameter(name = "items")
    @Parameter(name = "tip")
    public ResponseEntity<?> getAllCereri(@RequestParam(name = "page", required = false, defaultValue = "-1") int page,
                                           @RequestParam(name = "items", required = false, defaultValue = "0") int itemsPerPage,
                                           @RequestParam(name = "tip", required = false) CereriEntity.TipCerere tip) {

//        int ok = 0;
//        if (tip != null) {
//            ok = 1;
//            for (CereriEntity.TipCerere constant : CereriEntity.TipCerere.values()) {
//                if (constant.name().equals(tip.toString())) {
//                    ok = 0;
//                }
//            }
//        }
//
//        if (ok == 1)
//            return ResponseEntity.badRequest().body("Acest tip de cerere nu exista");
//
//        CereriEntity.TipCerere tipCerere= CereriEntity.TipCerere.valueOf(tip);
//        CereriEntity.TipCerere tipCerere= CereriEntity.TipCerere.valueOf(tip);
        return ResponseEntity.ok(cereriService.getAllCereri(page, itemsPerPage, tip));
    }

    @GetMapping("/api/student-stay/cereri/{id}")
    public Optional<CereriEntity> getCerereById(@PathVariable Long id) {
        return cereriService.getCerereById(id);
    }

    @GetMapping("/api/student-stay/cerere/user/{id}")
    public Optional<CereriEntity> getCerereByUserId(@PathVariable Long id,
                                                    @RequestHeader(value = "Authorization") String authorization,
                                                    @RequestHeader(value = "User-Role") String userRole) {
        return Optional.ofNullable(cereriService.getCerereByUserId(id, authorization, userRole));
    }

    @PostMapping("/api/student-stay/cereri")
    public CereriEntity addCerere(@RequestBody CereriEntity cerere,
                                  @RequestHeader(value = "Authorization") String authorization,
                                  @RequestHeader(value = "User-Role") String userRole) {
        return cereriService.addCerere(cerere, authorization, userRole);
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

    @DeleteMapping("/api/student-stay/cereri/{id}")
    public ResponseEntity<?> deleteCerere(@PathVariable Long id) {
        return ResponseEntity.ok(cereriService.deleteCerere(id));
    }

}
