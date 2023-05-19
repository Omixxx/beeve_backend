package it.unimol.vino.models.request;


import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class DecreaseTotalQuantityOfItemRequest {
    @NonNull()
    @Positive
    private Long id;

    @NonNull()
    @Positive
    private Integer quantity;

}
