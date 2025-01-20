package com.paw.laborator.repositories;

import com.paw.laborator.data.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAll(Pageable pageable);
    Page<Student> findByAn(Integer an, Pageable pageable);
    Page<Student> findByCnp(String cnp, Pageable pageable);
    Student findByCnp(String cnp);
    Page<Student> findByNumeContainingIgnoreCaseAndPrenumeContainingIgnoreCase(String nume, String prenume, Pageable pageable);
    Page<Student> findByNumeContainingIgnoreCase(String nume, Pageable pageable);
    Page<Student> findByPrenumeContainingIgnoreCase(String prenume, Pageable pageable);


    Student findByEmail(String email);
}
