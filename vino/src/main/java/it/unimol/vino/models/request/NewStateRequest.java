package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class NewStateRequest {

    @NonNull
    @NotBlank
    private String stateName;

    @NonNull
    private Boolean doesProduceWaste;
}
