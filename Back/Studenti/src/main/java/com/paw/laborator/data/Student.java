package com.paw.laborator.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    @NotBlank(message = "CNP-ul studentului este obligatoriu!")
    @Size(min=13, max=13, message = "CNP studentului trebuie sa contina exact 13 caractere.")
    private String cnp;

    @Column(nullable = false)
    @NotBlank(message = "Numele studentului este obligatoriu!")
    @Size(min = 3, max = 20, message = "Numele studentului trebuie sa contina 3-20 caract.")
    private String nume;

    @Column(nullable = false)
    @NotBlank(message = "Prenumele studentului este obligatoriu!")
    @Size(min = 5, max = 30, message = "Prenumele studentului trebuie sa contina 5-30 caract.")
    @Pattern(regexp = "^[A-Z]{1} [A-Z][a-zA-Z]*$", message = "Prenumele contine pe prima pozitie initiala tatalui, urmata de spatiu si prenume.")
    private String prenume;

    @Column(nullable=false, unique=true)
    @NotBlank(message="Emailul este obligatoriu!")
    //@Pattern( regexp = "^[a-zA-Z]+\\/[a-zA-Z]+@academic\\.tuiasi\\.ro$", message="Emailul trebuie sa aiba formatul: prenume.nume@academic.tuiasi.ro")
    private String email;

    @Column(nullable=false)
    @NotBlank(message = "Telefonul studentului este obligatoriu!")
    @Size(min=10, max=10, message = "Telefonul studentului trebuie sa contina exact 10 caractere.")
    private String nrtel;

    @Min(value = 1, message = "Media e minim 1.")
    @Max(value = 10, message = "Media e maxim 10.")
    private Double medie;

    @Column(nullable = false)
    @NotBlank(message = "Grupa este obligatorie!")
    @Pattern(regexp = "^[1-2][1-4][0-9]{2}[AB]$", message = "Grupa trebuie sa contina 5 caractere: primele 4 numerice (LICENTA-1/MASTER-2, an_studiu(1-4), urmatoarele 2 aleatorii), ultimul caracter seria(A sau B).")
    private String grupa;

    @Column(nullable = false)
    @Min(value = 1, message = "Anul de studiu e minim 1.")
    @Max(value = 4, message = "Anul de studiu e maxim 10.")
    private int an;

}
