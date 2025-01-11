package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.service.RepartizareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
