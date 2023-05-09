package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewProcessRequest {

    @NonNull
    @NotEmpty(message = "Il processo deve necessariamente avere almeno uno stato")
    private List<Long> states;
}
