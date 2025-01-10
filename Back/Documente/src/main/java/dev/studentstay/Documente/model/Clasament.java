package dev.studentstay.Documente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clasament")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Clasament {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "pozitie", nullable = false) // FIXME: nullable true?
    private Integer pozitie;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "prenume", nullable = false)
    private String prenume;

    @Column(name = "punctaj", nullable = false) // FIXME: nullable true?
    private Integer punctaj;

}
