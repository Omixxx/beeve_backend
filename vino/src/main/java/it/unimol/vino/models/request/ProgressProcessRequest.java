package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgressProcessRequest {

    @NotEmpty(message = "Se specificato il campo description non può essere vuoto")
    private String description;

    @NotNull(message = "E' necessario specificare lo scarto prodotto")
    @Positive(message = "Lo scarto prodotto deve essere maggiore di 0")
    private Integer waste;

}
