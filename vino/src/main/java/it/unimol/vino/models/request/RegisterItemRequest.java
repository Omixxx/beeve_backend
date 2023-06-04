package it.unimol.vino.models.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterItemRequest {

    @Min(value = 0, message = "la capacità deve essere maggiore di 0")
    @NonNull
    private Float capacity;

    private String description;

    @NotNull(message = "E' necessario specificare il fornitore")
    private Long provider_id;

    @NotNull(message = "E' necessario specificare la data di registrazione")
    private Date date;

    @NotNull(message = "E' necessario specificare la quantità")
    @Positive(message = "La quantità deve essere maggiore di 0")
    private Integer quantity;

    @NotNull(message = "E' necessario specificare il nome della categoria")
    private String categoryName;

    @NotNull(message = "E' necessario specificare il nome dell'item")
    private String name;

}
