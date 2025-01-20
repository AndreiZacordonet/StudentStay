package com.paw.laborator.controllers;

import com.paw.laborator.data.Student;
import com.paw.laborator.dtos.StudentDTO;
import com.paw.laborator.repositories.StudentRepository;
import com.paw.laborator.services.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})

@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentServiceImpl studentServiceImpl;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<List<StudentDTO>> createStudent(@Valid @RequestBody List<StudentDTO> studentiDTO,
                                                          @RequestHeader(value = "Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authorization.substring(7);

        if(token != null) {
        List<StudentDTO> studenti = studentServiceImpl.createStudent(studentiDTO);
        return new ResponseEntity<>(studenti, HttpStatus.CREATED); }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String prenume,
            @RequestParam(required = false) Integer an,
            @RequestParam(required = false) String grupa,
            @RequestParam(required = false) String cnp,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "7", required = false) int size,
            @RequestHeader(value = "Authorization") String authorization,
            PagedResourcesAssembler<StudentDTO> assembler) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authorization.substring(7);

        if(token != null) {
            Pageable pageable = PageRequest.of(page, size);

            Page<StudentDTO> students = studentServiceImpl.getAllStudents(pageable);

            if (nume != null) {
                students = new PageImpl<>(students.getContent().stream().filter(student -> student.getNume().equalsIgnoreCase(nume))
                                .collect(Collectors.toList()),pageable,students.getTotalElements());
            }

            if (prenume != null) {
                students = new PageImpl<>(students.getContent().stream().filter(student -> student.getPrenume().equalsIgnoreCase(prenume))
                                .collect(Collectors.toList()),pageable,students.getTotalElements());
            }

            if (grupa != null) {
                students = new PageImpl<>(students.getContent().stream().filter(student -> student.getGrupa().equalsIgnoreCase(grupa))
                                .collect(Collectors.toList()),pageable,students.getTotalElements());
            }

            if (an != null) {
                students = new PageImpl<>(students.getContent().stream().filter(student -> student.getAn() == an)
                                .collect(Collectors.toList()),pageable,students.getTotalElements());
            }

            if (cnp != null) {
                students = new PageImpl<>(students.getContent().stream().filter(student -> student.getCnp().equals(cnp))
                        .collect(Collectors.toList()), pageable, students.getTotalElements());
            }

            PagedModel<EntityModel<StudentDTO>> pagedModel = assembler.toModel(students);

            return ResponseEntity.ok(pagedModel);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id,
                                                     @RequestHeader(value = "Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authorization.substring(7);

        // Add role checks
        if(token != null) {
            StudentDTO studentDTO = studentServiceImpl.getStudentById(id);
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/search/get-id-by-cnp/{cnpStudent}")
    public ResponseEntity<?> getIdByCnp(@PathVariable String cnpStudent,
                                        @RequestHeader(value = "Authorization") String authorization) {
        Student student = studentRepository.findByCnp(cnpStudent);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student.getId());
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {

        return ResponseEntity.ok(studentRepository.findByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO studentDTO,
                                                    @RequestHeader(value = "Authorization") String authorization,
                                                    @RequestHeader(value = "User-Role") String userRole) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authorization.substring(7);

        if(token != null && "admin".equalsIgnoreCase(userRole)) {
            StudentDTO updatedStudent = studentServiceImpl.updateStudent(id, studentDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id,
                                              @RequestHeader(value = "Authorization") String authorization,
                                              @RequestHeader(value = "User-Role") String userRole) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authorization.substring(7);

        if(token != null && "admin".equalsIgnoreCase(userRole)) {
            studentServiceImpl.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
