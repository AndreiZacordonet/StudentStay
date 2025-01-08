package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.dto.DocumentDto;
import dev.studentstay.Documente.model.Acte;
import dev.studentstay.Documente.service.DocumenteService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documente")
public class DocumenteController {

    private final DocumenteService documenteService;

    public DocumenteController(DocumenteService documenteService) {
        this.documenteService = documenteService;
    }

    @GetMapping
    public ResponseEntity<?> getDocumente(Pageable pageable,
                                          @RequestParam(name = "cnpStudent", required = false) String cnpStudent,
                                          @RequestParam(name = "act", required = false) Acte act,
                                          @RequestHeader(value = "Authorization") String authorization,
                                          @RequestHeader(value = "User-Role") String userRole) {

        return ResponseEntity.ok(documenteService.getDocumente(pageable, authorization, userRole, cnpStudent, act));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id,
                                     @RequestHeader(value = "Authorization") String authorization,
                                     @RequestHeader(value = "User-Role") String userRole) {
        return ResponseEntity.ok(documenteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createNew(@RequestParam("idStudent") Long idStudent,
                                       @RequestParam("documentName") String documentName,
                                       @RequestPart("file") MultipartFile file) {

        try {
            DocumentDto newDoc = new DocumentDto(idStudent, documentName);
            return ResponseEntity.ok(documenteService.createNew(newDoc, file));
        } catch (IOException e) {
            return (ResponseEntity<?>) ResponseEntity.unprocessableEntity();
        }
    }

}
