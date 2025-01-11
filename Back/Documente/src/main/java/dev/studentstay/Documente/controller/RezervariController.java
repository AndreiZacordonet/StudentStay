package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.dto.RezervareDto;
import dev.studentstay.Documente.model.Rezervare;
import dev.studentstay.Documente.service.RezervareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
