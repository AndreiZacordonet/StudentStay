package com.paw.laborator.config;

import com.paw.laborator.data.Student;
import com.paw.laborator.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

//    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepository
    ) {
        return args -> {
            Student student1 = new Student(null, "1234567890123", "Popescu", "A Andrei",
                    "andrei.popescu@academic.tuiasi.ro", "0712345678", 9.5, "1213A", 3);
            Student student2 = new Student(null, "2345678901234", "Ionescu", "B Maria",
                    "maria.ionescu@academic.tuiasi.ro", "0723456789", 8.2, "2314B", 2);
            Student student3 = new Student(null, "3456789012345", "Vasilescu", "C Mihai",
                    "mihai.vasilescu@academic.tuiasi.ro", "0734567890", 7.8, "1412A", 4);
            Student student4 = new Student(null, "4567890123456", "Georgescu", "D Ana",
                    "ana.georgescu@academic.tuiasi.ro", "0745678901", 9.0, "1214B", 1);
            Student student5 = new Student(null, "5678901234567", "Dumitrescu", "E Vlad",
                    "vlad.dumitrescu@academic.tuiasi.ro", "0756789012", 6.5, "1311A", 2);
            Student student6 = new Student(null, "6789012345678", "Stoicescu", "F Elena",
                    "elena.stoicescu@academic.tuiasi.ro", "0767890123", 9.7, "2413B", 3);
            Student student7 = new Student(null, "7890123456789", "Marinescu", "G Ioana",
                    "ioana.marinescu@academic.tuiasi.ro", "0778901234", 8.9, "2212A", 4);

            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);
            studentRepository.save(student5);
            studentRepository.save(student6);
            studentRepository.save(student7);

            log.info("Database initialized with 7 students");
        };
    }
}
