package it.unimol.vino.models.request;


import it.unimol.vino.models.entity.ProviderSupplyItem;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterItemRequest {

    @NonNull()
    @Positive
    private Long capacity;

    @NonNull()
    private String description;

    @NonNull()
    private Long provider_id;

    @NonNull()
    private ProviderSupplyItem providerSupplyItem;


}
