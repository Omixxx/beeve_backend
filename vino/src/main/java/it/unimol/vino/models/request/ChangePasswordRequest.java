package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotNull(message = "La vecchia password non può essere nulla")
    private String oldPassword;

    @NotNull(message = "La nuova password non può essere nulla")
    private String newPassword;
}
