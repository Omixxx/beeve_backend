package it.unimol.vino.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    @Email(message = "E' necessario inserire una mail valida")
    @NotNull(message = "E' necessario inserire una mail")
    private String email;

    @NotNull(message = "E' necessario inserire una password")
    private String password;
}
