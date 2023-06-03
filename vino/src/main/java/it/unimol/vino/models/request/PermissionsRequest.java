package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionsRequest {

    @NotNull(message = "Il campo canRead non può essere nullo")
    private Boolean canRead;

    @NotNull(message = "Il campo canWrite non può essere nullo")
    private Boolean canWrite;

    @NotNull(message = "Il campo canDelete non può essere nullo")
    private Boolean canDelete;

    @NotNull(message = "Il campo canUpdate non può essere nullo")
    private Boolean canUpdate;
}
