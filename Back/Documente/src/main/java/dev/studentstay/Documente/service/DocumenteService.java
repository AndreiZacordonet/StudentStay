package dev.studentstay.Documente.service;

import dev.studentstay.Documente.dto.DocumentDto;
import dev.studentstay.Documente.exceptions.DocumentFaraCerereException;
import dev.studentstay.Documente.exceptions.DocumentNotFoundException;
import dev.studentstay.Documente.exceptions.TextExtractionException;
import dev.studentstay.Documente.model.Acte;
import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.model.Documente;
import dev.studentstay.Documente.repository.CereriRepository;
import dev.studentstay.Documente.repository.DocumenteRepository;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumenteService {

    private final DocumenteRepository documenteRepository;
    private final CereriRepository cereriRepository;
    private final StudentServiceClient studentServiceClient;

    @Value("${file.system.path}")
    private String fileSystemPath;

    @Value("${ocr.api.key}")
    private String ocrApiKey;

    public DocumenteService(DocumenteRepository documenteRepository, CereriRepository cereriRepository, StudentServiceClient studentServiceClient) {
        this.documenteRepository = documenteRepository;
        this.cereriRepository = cereriRepository;
        this.studentServiceClient = studentServiceClient;
    }

    public Page<Documente> getDocumente(Pageable pageable, String authorization,
                                        String cnpStudent, Acte act) {

        System.out.println("\n\ncnp: " + cnpStudent + "\n\n");

        return documenteRepository.findAll(Specification.where(
                studentIdEquals(cnpStudent != null ? studentServiceClient.getStudentIdByCnp(cnpStudent, authorization, "userRole")
                        : null)
                        .and(acteEquals(act))
        ), pageable);
    }

    public static Specification<Documente> studentIdEquals(Long idStudent) {
        return ((root, query, criteriaBuilder) ->
                idStudent == null ? null : criteriaBuilder.equal(root.get("idStudent"), idStudent));
    }

    public static Specification<Documente> acteEquals(Acte act) {
        return ((root, query, criteriaBuilder) ->
                act == null ? null : criteriaBuilder.equal(root.get("acte"), act));
    }

    public Documente getById(Long id) {
        return documenteRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Documentul cu id '" + id + "' nu a fost gasit."));
    }

    public Documente createNew(DocumentDto newDoc, MultipartFile file) throws IOException {
        if (cereriRepository.findByUserId(newDoc.getIdStudent()) == null) {
            throw new DocumentFaraCerereException("Studentul cu id '" + newDoc.getIdStudent() + "' nu a trimis cerere.");
        }

        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            throw new IllegalArgumentException("Invalid file. Only non-empty .pdf files are accepted.");
        }

        String filePath = "src/main/resources/files" + "/" + newDoc.getIdStudent() + "_" + newDoc.getDocumentName();
        Path destinationPath = Paths.get(filePath);

        Documente existingDocument = documenteRepository.searchDocumenteByIdStudent(newDoc.getIdStudent());//TODO: get one document by student id
        if (existingDocument != null) {

            File oldFile = new File(existingDocument.getCaleDocument());
            if (oldFile.exists()) {
                oldFile.delete();
            }

            existingDocument.setCaleDocument(filePath);
            documenteRepository.save(existingDocument);

            Files.createDirectories(destinationPath.getParent());
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return existingDocument;
        } else {
            Documente doc = new Documente(null, newDoc.getIdStudent(), filePath, null, null);
            documenteRepository.save(doc);

            Files.createDirectories(destinationPath.getParent());
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return doc;
        }
    }

    public boolean extractText(Long id) {
        Documente documente = documenteRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Document with id " + id + " not found"));

        String filePath = documente.getCaleDocument();
        File pdfFile = new File(filePath);

        if (!pdfFile.exists()) {
            throw new IllegalArgumentException("File not found at path: " + filePath);
        }

        try {
            byte[] fileBytes = Files.readAllBytes(pdfFile.toPath());

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("apikey", ocrApiKey);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return pdfFile.getName();
                }
            });
            body.add("isOverlayRequired", "false");

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String apiUrl = "https://api.ocr.space/parse/image";


            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {

                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> parsedResult = (Map<String, Object>) ((List<?>) responseBody.get("ParsedResults")).get(0);
                String extractedText = (String) parsedResult.get("ParsedText");
                System.out.println("Extracted Text: " + extractedText);

                documente.setContinut(extractedText);
                documenteRepository.save(documente);

                return true;
            } else {
                System.err.println("OCR API Error: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void extractAllText(Long id) {
        if (id == null) {
            List<Documente> allDocuments = documenteRepository.findAll();
            for (Documente document : allDocuments) {
                boolean result = extractText(document.getId());
                if (!result) {
                    throw new TextExtractionException("Failed to extract text for document with id: " + document.getId());
                }
            }
        } else {
            boolean result = extractText(id);
            if (!result) {
                throw new TextExtractionException("Failed to extract text for document with id: " + id);
            }
        }
    }

    private static final Map<Acte, List<String>> ACTE_KEYWORDS = Map.of(
            Acte.BULETIN, List.of("carte de identitate", "identity card", "CNP"),
            Acte.CERTIFICAT_NASTERE, List.of("certificat de nastere", "birth certificate"),
            Acte.CERTIFICAT_DIVORT, List.of("certificat de divort", "divorce certificate"),
            Acte.CERTIFICAT_DECES, List.of("certificat de deces", "death certificate"),
            Acte.CERTIFICAT_HANDICAP, List.of("certificat de handicap", "disability certificate"),
            Acte.ALTE_ACTE, List.of("document", "unknown", "alte acte")
    );

    public void processAllDocuments(Long id) {
        if (id == null) {
            List<Documente> allDocuments = documenteRepository.findAll();
            for (Documente document : allDocuments) {
                boolean result = processDocument(document.getId());
                if (!result) {
                    throw new TextExtractionException("Failed to process text for document with id: " + document.getId());
                }
            }
        } else {
            boolean result = processDocument(id);
            if (!result) {
                throw new TextExtractionException("Failed to process text for document with id: " + id);
            }
        }
    }

    private boolean processDocument(Long id) {
        Documente doc = documenteRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Documentul cu id '" + id + "' nu a fost gasit."));

        String content = doc.getContinut();

        System.out.println("\nContent: " + content + "\n");

        if (content != null && !content.isBlank()) {
            List<Map<Acte, String>> acteList = new ArrayList<>();

            String[] documentSegments = content.split("\\r?\\n\\s*");

            for (String segment : documentSegments) {
                Acte documentType = classifyDocumentTypeFlexible(segment);
                String personName = extractPersonName(segment);

                if (documentType != null && personName != null) {
                    acteList.add(Map.of(documentType, personName));
                }
            }

            System.out.println("\nActe list: " + acteList + "\n");

            doc.setActe(acteList);

            documenteRepository.save(doc);

            return true;
        }

        return false;
    }

    private Acte classifyDocumentTypeFlexible(String text) {

        Map<Acte, List<String>> acteKeywords = Map.of(
                Acte.BULETIN, List.of("carte de identitate", "identity card"),
                Acte.CERTIFICAT_NASTERE, List.of("certificat nastere", "birth certificate"),
                Acte.CERTIFICAT_DIVORT, List.of("certificat divort", "divorce certificate"),
                Acte.CERTIFICAT_DECES, List.of("certificat deces", "death certificate"),
                Acte.CERTIFICAT_HANDICAP, List.of("certificat handicap", "disability certificate")
        );

        LevenshteinDistance distance = new LevenshteinDistance();

        Acte bestMatch = Acte.ALTE_ACTE;
        int bestScore = Integer.MAX_VALUE;

        for (Map.Entry<Acte, List<String>> entry : acteKeywords.entrySet()) {
            for (String keyword : entry.getValue()) {
                int score = distance.apply(text.toLowerCase(), keyword.toLowerCase());
                if (score < bestScore && score <= 5) {
                    bestScore = score;
                    bestMatch = entry.getKey();
                }
            }
        }

        return bestMatch;
    }

    private String extractPersonName(String text) {

        Pattern regex = Pattern.compile("\\b\\p{Lu}{2,}(\\s+\\p{Lu}{2,}){1,2}\\b");
        Matcher matcher = regex.matcher(text);

        List<String> candidates = new ArrayList<>();
        while (matcher.find()) {
            candidates.add(matcher.group().trim());
        }

        return candidates.stream().max(Comparator.comparingInt(String::length)).orElse(null);
    }

}
