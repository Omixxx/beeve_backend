package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewStateRequest {
    @NonNull()
    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description = null;

    private String wasteUnit = null;


}
