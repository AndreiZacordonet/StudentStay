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

    public Long getStudentIdByCnp(String cnp, String authToken, String userRole) {
        String url = studentServiceUrl + "/api/students/search/get-id-by-cnp/" + cnp;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        headers.set("User-Role", userRole);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Long> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Long.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null; // Student not found
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to Student service", e);
        }
    }

}
