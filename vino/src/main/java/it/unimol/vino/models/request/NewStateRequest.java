package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class NewStateRequest {

    @NotBlank(message = "Il nome dello stato non può essere vuoto")
    private String stateName;

    @NotNull(message = "E' necessario specificare se lo stato produce rifiuti")
    private Boolean doesProduceWaste;
}
