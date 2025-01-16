package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.service.ClasamentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clasament")
public class ClasamentController {

    private final ClasamentService clasamentService;

    public ClasamentController(ClasamentService clasamentService) {
        this.clasamentService = clasamentService;
    }

    @PostMapping()
    public ResponseEntity<?> populateClasament(@RequestHeader(value = "Authorization") String authorization) {

        clasamentService.populate(authorization);

        return ResponseEntity.ok("Totul ok!");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> modifyOne(@PathVariable Long id,
                                       @RequestParam(name = "punctaj") Double punctaj,
                                       @RequestHeader(value = "Authorization") String authorization) {

        return ResponseEntity.ok(clasamentService.modifyOne(id, punctaj, authorization));
    }

    @GetMapping()
    @Parameter(name = "page")
    @Parameter(name = "items")
    @Parameter(name = "email")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(name = "items", required = false, defaultValue = "10") int itemsPerPage,
                                          @RequestParam(name = "email", required = false) String email) {

        return ResponseEntity.ok(clasamentService.getAll(page, itemsPerPage, email));
    }
}
