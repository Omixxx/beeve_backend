package it.unimol.vino.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateProviderRequest {

    @NonNull()
    private String oldName;

    @NonNull()
    @NotBlank(message = "name must be not blank")
    private String newName;

    @NonNull()
    @NotBlank(message = "Numero di telefono non può essere vuoto")
    private String phone_number;

    @NonNull()
    @Email(message = "Email deve essere valida")
    @NotBlank(message = "Email non può essere vuota")
    private String email;



}
