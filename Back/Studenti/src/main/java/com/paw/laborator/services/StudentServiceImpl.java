package com.paw.laborator.services;

import com.paw.laborator.data.Student;
import com.paw.laborator.dtos.StudentDTO;
import com.paw.laborator.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public List<StudentDTO> createStudent(List<StudentDTO> studentDTOList) {
        List<StudentDTO> studenti = new ArrayList<>();
        for (StudentDTO studentDTO : studentDTOList) {
            Student student = new Student();
            student.setCnp(studentDTO.getCnp());
            student.setNume(studentDTO.getNume());
            student.setPrenume(studentDTO.getPrenume());
            student.setEmail(studentDTO.getEmail());
            student.setNrtel(studentDTO.getNrtel());
            student.setMedie(studentDTO.getMedie());
            student.setGrupa(studentDTO.getGrupa());
            student.setAn(studentDTO.getAn());

            studentRepository.save(student);
            studentDTO.setId(student.getId());
            studenti.add(studentDTO);
        }
        return studenti;
    }

    @Override
    public Page<StudentDTO> getAllStudents(Pageable page) {
        Page<Student> studentPage = studentRepository.findAll(page);
        return studentPage.map(student -> {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setCnp(student.getCnp());
            studentDTO.setNume(student.getNume());
            studentDTO.setPrenume(student.getPrenume());
            studentDTO.setEmail(student.getEmail());
            studentDTO.setNrtel(student.getNrtel());
            studentDTO.setMedie(student.getMedie());
            studentDTO.setGrupa(student.getGrupa());
            studentDTO.setAn(student.getAn());
            return studentDTO;
        });
    }

    @Override
    public StudentDTO getStudentById(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Studentul nu exista"));
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(student.getId());
        studentDTO.setCnp(student.getCnp());
        studentDTO.setNume(student.getNume());
        studentDTO.setPrenume(student.getPrenume());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setNrtel(student.getNrtel());
        studentDTO.setMedie(student.getMedie());
        studentDTO.setGrupa(student.getGrupa());
        studentDTO.setAn(student.getAn());
        return studentDTO;
    }

    @Override
    public StudentDTO updateStudent(Integer id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Studentul nu exista"));
        student.setCnp(studentDTO.getCnp());
        student.setNume(studentDTO.getNume());
        student.setPrenume(studentDTO.getPrenume());
        student.setEmail(studentDTO.getEmail());
        student.setNrtel(studentDTO.getNrtel());
        student.setMedie(studentDTO.getMedie());
        student.setGrupa(studentDTO.getGrupa());
        student.setAn(studentDTO.getAn());

        studentRepository.save(student);
        studentDTO.setId(student.getId());
        return studentDTO;
    }

    @Override
    public void deleteStudent(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Studentul nu exista"));
        studentRepository.delete(student);
    }
}
