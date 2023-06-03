package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AddItemToProcessRequest {

    @NotNull(message = "L'id dell'item non può essere nullo")
    private Long itemId;

    @NotNull(message = "L'id del processo non può essere nullo")
    private Long processId;

    @NotNull(message = "La quantità non può essere nulla")
    @Positive(message = "La quantità deve essere maggiore di 0")
    private Integer usedQuantity;
}
