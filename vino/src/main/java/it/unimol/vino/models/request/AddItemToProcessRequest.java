package it.unimol.vino.models.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AddItemToProcessRequest {

    @NonNull
    private Long itemId;

    @NonNull
    private Long processId;

    @NonNull
    @Positive
    private Integer usedQuantity;
}
