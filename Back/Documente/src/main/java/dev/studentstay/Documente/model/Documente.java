package dev.studentstay.Documente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
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
@Table(name = "documente")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Documente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_student", nullable = false)
    private Long idStudent;

    @Column(name = "cale_document", nullable = false)
    private String caleDocument;

    @Column(name = "continut", columnDefinition = "TEXT")
    private String continut;

    @Type(JsonType.class)
    @Column(name = "acte", columnDefinition = "json")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<Acte, String>> acte;
}
