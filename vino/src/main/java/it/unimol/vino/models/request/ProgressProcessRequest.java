package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgressProcessRequest {
    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private Integer waste;

}
