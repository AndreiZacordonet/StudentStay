package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.dto.RezervareDto;
import dev.studentstay.Documente.model.Rezervare;
import dev.studentstay.Documente.service.RezervareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rezervari")
public class RezervariController {

    private final RezervareService rezervareService;

    public RezervariController(RezervareService rezervareService) {
        this.rezervareService = rezervareService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody RezervareDto rezervareDto) {
        try {
            Rezervare createdRezervare = rezervareService.create(rezervareDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRezervare);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Rezervare> getRezervareByEmail(@PathVariable String email) {

        Rezervare rezervare = rezervareService.getRezervareByEmail(email);
        
        return ResponseEntity.ok(rezervare);
    }
}
