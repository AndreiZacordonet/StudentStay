package com.paw.laborator.services;

import com.paw.laborator.dtos.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    List<StudentDTO> createStudent(List<StudentDTO> studentDTOList);
    Page<StudentDTO> getAllStudents(Pageable page);
    StudentDTO getStudentById(Integer id);
    StudentDTO updateStudent(Integer id, StudentDTO studentDTO);
    void deleteStudent(Integer id);
}
