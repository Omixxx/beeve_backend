package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NewProcessRequest {

    @NonNull()
    private Date date;

    private String name;
}
