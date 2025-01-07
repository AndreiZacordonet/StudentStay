package dev.studentstay.Documente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "cereri",uniqueConstraints = { @UniqueConstraint(columnNames = "userId") })
@NoArgsConstructor
public class CereriEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TipCerere tipCerere;

    public enum TipCerere {
        CAZARE,
        CASATORIT,
        HANDICAP,
        ORFAN
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTipCerere(TipCerere tipCerere) {
        this.tipCerere = tipCerere;
    }


}


