package com.paw.laborator.dtos;

import com.paw.laborator.data.Student;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO extends RepresentationModel<StudentDTO> {
    private Integer id;
    private String cnp;
    private String nume;
    private String prenume;
    private String email;
    private String nrtel;
    private Double medie;
    private String grupa;
    private int an;

}
