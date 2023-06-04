package it.unimol.vino.models.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank(message = "E' necessario specificare il nome")
    private String firstName;

    @NotBlank(message = "E' necessario specificare il cognome")
    private String lastName;

    @Email(message = "la mail deve essere valida")
    @NotBlank(message = "E' necessario specificare la mail")
    private String email;

    @NotBlank(message = "E' necessario specificare la password")
    private String password;

    @NotNull(message = "E' necessario specificare se l'utente è un admin o meno")
    private Boolean isAdmin;

    @NotNull(message = "E' necessario specificare l'età")
    @Min(value = 18, message = "l'operaio deve essere maggiorenne")
    @Max(value = 70, message = "l'operaio non puo avere piu di 70 anni")
    private Integer age;
}
