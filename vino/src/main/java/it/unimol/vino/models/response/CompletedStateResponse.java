package it.unimol.vino.models.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CompletedStateResponse {

    @NonNull
    @NotBlank
    @NotEmpty
    private String description;

    @NonNull
    @Min(0)
    private Integer waste;
}
