package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.dto.DocumentDto;
import dev.studentstay.Documente.exceptions.DocumentNotFoundException;
import dev.studentstay.Documente.model.Acte;
import dev.studentstay.Documente.model.Documente;
import dev.studentstay.Documente.repository.DocumenteRepository;
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
    private final DocumenteRepository documenteRepository;

    public DocumenteController(DocumenteService documenteService, DocumenteRepository documenteRepository) {
        this.documenteService = documenteService;
        this.documenteRepository = documenteRepository;
    }

    @GetMapping
    public ResponseEntity<?> getDocumente(Pageable pageable,
                                          @RequestParam(name = "cnpStudent", required = false) String cnpStudent,
                                          @RequestParam(name = "act", required = false) Acte act,
                                          @RequestHeader(value = "Authorization") String authorization) {

        return ResponseEntity.ok(documenteService.getDocumente(pageable, authorization, cnpStudent, act));
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

    @PostMapping("/extract-text")
    public ResponseEntity<?> extractText(@RequestParam(required = false) Long id,
                                         @RequestHeader(value = "Authorization") String authorization) {
        documenteService.extractAllText(id);
        return ResponseEntity.ok("Text extraction completed successfully.");
    }

    @PostMapping("/process-text")
    public ResponseEntity<?> processAllDocuments(@RequestParam(required = false) Long id,
                                         @RequestHeader(value = "Authorization") String authorization) {
        documenteService.processAllDocuments(id);
        return ResponseEntity.ok("Text processing completed successfully.");
    }

    @PatchMapping("/update-text/{id}")
    public ResponseEntity<?> updateText(@PathVariable(name = "id") Long id,
                                        @RequestBody String newText) {
        Documente document = documenteRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Documentul cu id '" + id + "' nu a fost gasit"));

        document.setContinut(newText);

        return ResponseEntity.ok(documenteRepository.save(document));
    }

}
