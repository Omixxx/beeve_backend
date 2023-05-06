package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashMap;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewProcessRequest {

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente avere almeno uno stato")
    private HashMap<Long, Integer> stateIdSequence;

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente avere almeno un item")
    private HashMap<Long, Integer> itemIdUsedQuantity;
}
