package dev.studentstay.Documente.service;

import dev.studentstay.Documente.dto.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<StudentDto> getAllStudents(String authToken, String userRole) {
        String url = studentServiceUrl + "/api/students?page={page}&size={size}";
        int page = 0;
        int size = 7; // Default size used by the Student service
        List<StudentDto> allStudents = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        headers.set("User-Role", userRole);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            while (true) {
                Map<String, Integer> params = new HashMap<>();
                params.put("page", page);
                params.put("size", size);

                ResponseEntity<PagedModel<StudentDto>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PagedModel<StudentDto>>() {},
                        params
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    PagedModel<StudentDto> pagedModel = response.getBody();
                    allStudents.addAll(pagedModel.getContent());

                    // Check if there are more pages
                    if (!pagedModel.hasLink("next")) {
                        break; // No more pages
                    }
                } else {
                    break; // Stop fetching if response is not OK
                }

                page++; // Increment page number for the next request
            }
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to Student service", e);
        }

        return allStudents;
    }


}
