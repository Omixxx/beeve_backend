package it.unimol.vino.models.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterItemRequest {

    @Min(0)
    @NonNull
    private Float capacity;

    private String description;

    @NonNull()
    private Long provider_id;

    @NonNull()
    private Date date;

    @NonNull()
    @Positive
    private Integer quantity;

    @NonNull()
    private String categoryName;

    @NonNull()
    private String name;

}
