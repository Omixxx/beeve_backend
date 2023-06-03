package it.unimol.vino.models.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Il nome della categoria non può essere vuoto")
    private String name;

    @NotNull(message = "E' necessario specificare se la categoria è primaria o meno")
    private Boolean isPrimary;
}
