package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewProcessRequest {

    @NotNull(message = "E' necessario inserire la lista degli id degli stati per creare un processo")
    @NotEmpty(message = "Il processo deve necessariamente avere almeno uno stato")
    private List<Long> states;

    @NotNull(message = "E' necessario inserire la lista degli id degli item per creare un processo")
    @NotEmpty(message = "Il processo deve necessariamente avere almeno un item")
    private HashMap<Long, Integer> itemIdUsedQuantity;

    @NotNull(message = "E' necessario inserire la lista degli id dei conferimenti per creare un processo")
    @NotEmpty(message = "Il processo deve necessariamente utilizzare almeno un conferimento")
    private HashMap<Long, Double> contributionIdQuantity;
}
