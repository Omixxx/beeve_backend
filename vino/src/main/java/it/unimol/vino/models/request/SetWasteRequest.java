package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetWasteRequest {

    @NotNull(message = "E' necessario inserire la quantità di rifiuti prodotti")
    private Long waste;

    @NotNull(message = "E' necessario inserire l'id del processo")
    private Long processId;

    @NotNull(message = "E' necessario inserire l'id dello stato")
    private Long stateId;

}
