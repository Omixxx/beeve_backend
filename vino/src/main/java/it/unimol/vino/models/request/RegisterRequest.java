package it.unimol.vino.models.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NonNull()
    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NonNull()
    @NotBlank(message = "lastName must be not blank")
    private String lastName;

    @NonNull()
    @Email(message = "email must be valid")
    @NotBlank(message = "email must be not blank")
    private String email;

    @NonNull()
    @NotBlank(message = "password must be not blank")
    private String password;
}
