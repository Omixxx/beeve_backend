package it.unimol.vino.models.request;


import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class DecreaseTotalQuantityOfItemRequest {
    @NonNull()
    @Positive
    private Long capacity;

    @NonNull()
    @Positive
    private Integer quantity;

    @NonNull()
    private String categoryName;

    @NonNull()
    private String name;
}
