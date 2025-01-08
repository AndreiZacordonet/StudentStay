package dev.studentstay.Documente.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Dto for creating a document")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long idStudent;

    private String documentName;
}
