package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.service.ClasamentService;
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
}
