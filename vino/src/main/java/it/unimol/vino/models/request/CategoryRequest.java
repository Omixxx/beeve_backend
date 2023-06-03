package it.unimol.vino.models.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryRequest {

    @NotEmpty(message = "Il nome della categoria non può essere vuoto")
    private String name;

    private Boolean isPrimary;
}
