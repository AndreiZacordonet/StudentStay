package dev.studentstay.Documente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonType;
import dev.studentstay.Documente.model.CoduriCamine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Schema(description = "Dto for creating a rezervation")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RezervareDto {

    private String email;

    @Type(JsonType.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<CoduriCamine, List<String>>> camere;

    @Type(JsonType.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> colegiCamera;  // list of emails that need to be checked

}
