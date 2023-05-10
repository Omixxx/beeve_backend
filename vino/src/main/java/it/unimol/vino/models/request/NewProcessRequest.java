package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewProcessRequest {

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente avere almeno uno stato")
    private List<Long> states;

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente avere almeno un item")
    private HashMap<Long, Integer> itemIdUsedQuantity;

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente utilizzare almeno un conferimento")
    private HashMap<Long, Double> contributionIdQuantity;
}
