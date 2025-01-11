package dev.studentstay.Documente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "repartizari")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Repartizare {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "camin")
    private CoduriCamine camin;

    @Column(name = "camera")
    private String camera;
}
