package dev.studentstay.Documente.dto;

import dev.studentstay.Documente.model.CoduriCamine;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditRepartizareRequest {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Camin cannot be null.")
    private CoduriCamine camin;

    @NotBlank(message = "Room cannot be blank.")
    private String room;
}

