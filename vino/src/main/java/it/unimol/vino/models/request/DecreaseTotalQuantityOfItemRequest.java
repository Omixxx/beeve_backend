package it.unimol.vino.models.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class DecreaseTotalQuantityOfItemRequest {

    @NotNull(message = "E' necessario specificare l'id dell'item")
    private Long id;

    @NotNull(message = "E' necessario specificare la quantità da sottrarre")
    @Positive(message = "La quantità deve essere maggiore di 0")
    private Integer quantity;

}
