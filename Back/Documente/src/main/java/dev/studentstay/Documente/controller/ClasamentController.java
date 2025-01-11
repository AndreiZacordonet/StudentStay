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
    public ResponseEntity<?> populateClasament(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestHeader(value = "User-Role") String userRole) {

        clasamentService.populate(authorization, userRole);

        return ResponseEntity.ok("Totul ok!");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> modifyOne(@PathVariable Long id,
                                       @RequestParam(name = "punctaj") Double punctaj,
                                       @RequestHeader(value = "Authorization") String authorization,
                                       @RequestHeader(value = "User-Role") String userRole) {

        return ResponseEntity.ok(clasamentService.modifyOne(id, punctaj, authorization, userRole));
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
