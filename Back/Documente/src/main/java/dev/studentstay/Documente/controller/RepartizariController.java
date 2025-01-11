package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.dto.EditRepartizareRequest;
import dev.studentstay.Documente.model.CoduriCamine;
import dev.studentstay.Documente.model.Repartizare;
import dev.studentstay.Documente.service.RepartizareService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repartizari")
@RequiredArgsConstructor
public class RepartizariController {

    private final RepartizareService repartizareService;

    @PostMapping("/populate")
    public ResponseEntity<String> populateRepartizare() {
        try {
            repartizareService.populate();

            return ResponseEntity.ok("Repartizare completed successfully.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during repartizare population: " + e.getMessage());
        }
    }

    @PatchMapping()
    public ResponseEntity<?> editByEmail(@RequestBody @Valid EditRepartizareRequest request) {
        try {
            repartizareService.editByEmail(request.getEmail(), request.getCamin(), request.getRoom());

            return ResponseEntity.ok("Repartizare updated successfully.");

        } catch (EntityNotFoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Repartizare> getRepartizareByEmail(@PathVariable String email) {
        try {
            Repartizare repartizare = repartizareService.getByEmail(email);

            return ResponseEntity.ok(repartizare);

        } catch (EntityNotFoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            
        }
    }

}
