package ProiectPAWCereri.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cereri",uniqueConstraints = { @UniqueConstraint(columnNames = "userId") })
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TipCerere getTipCerere() {
        return tipCerere;
    }

    public void setTipCerere(TipCerere tipCerere) {
        this.tipCerere = tipCerere;
    }


}


