package it.unimol.vino.models.request;


import it.unimol.vino.models.entity.ProviderSupplyItem;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class MappingItemRequest {

    @NonNull()
    private Long item_id;

    @NonNull()
    private Long provider_id;

    @NonNull()
    private Date date;

    @NonNull()
    private Long quantity;
}
