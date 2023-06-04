package it.unimol.vino.models.request;

import it.unimol.vino.models.enums.SectorName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePermissionsRequest {

    @NotNull(message = "E' necessario inserire l'email dell'utente")
    @Email(message = "Il campo email deve essere un indirizzo email valido")
    private String email;

    @NotNull(message = "E' necessario specificare i permessi per i settori")
    private HashMap<@Valid @NonNull SectorName, @Valid @NotNull PermissionsRequest> permissions;

}
