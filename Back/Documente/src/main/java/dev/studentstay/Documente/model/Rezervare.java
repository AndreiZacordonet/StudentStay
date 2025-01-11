package dev.studentstay.Documente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rezervari")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rezervare {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Type(JsonType.class)
    @Column(name = "camere", columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<CoduriCamine, List<String>>> camere;

    @Type(JsonType.class)
    @Column(name = "colegi_camera", columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> colegiCamera;
}
