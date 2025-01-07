package dev.studentstay.Documente.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentServiceClient {

    private final RestTemplate restTemplate;

    @Value("${student.service.url}")
    private String studentServiceUrl;

    public StudentServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkIfStudentExists(Long studentId, String authToken, String userRole) {
        String url = studentServiceUrl + "/api/students/" + studentId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        headers.set("User-Role", userRole);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Void.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false; // Student not found
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to Student service", e);
        }
    }
}
