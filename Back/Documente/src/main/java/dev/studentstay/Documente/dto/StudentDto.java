package dev.studentstay.Documente.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto> {
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
