package it.unimol.vino.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
