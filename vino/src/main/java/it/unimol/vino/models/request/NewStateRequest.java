package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class NewStateRequest {

    @NonNull
    @NotBlank
    private String stateName;

    @NonNull
    private Boolean doesProduceWaste;
}
