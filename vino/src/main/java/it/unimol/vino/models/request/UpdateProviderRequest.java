package it.unimol.vino.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateProviderRequest {

    @NotNull(message = "E' necessario specificare il vecchio nome")
    private String oldName;

    @NotBlank(message = "E' necessario specificare il nuovo nome")
    private String newName;

    @NotBlank(message = "E' necessario specificare il nuovo numero di telefono")
    private String phone_number;

    @Email(message = "Email deve essere valida")
    @NotBlank(message = "E' necessario specificare la email")
    private String email;


}
