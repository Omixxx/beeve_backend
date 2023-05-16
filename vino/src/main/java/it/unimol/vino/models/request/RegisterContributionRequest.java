package it.unimol.vino.models.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterContributionRequest {

    @NonNull
    private String origin;

    @NonNull
    private String country;

    private String photoURL;

    private String description;

    @Positive
    private double sugarDegree;

    @Positive
    private double quantity;

    @NonNull
    private Date date;

    @NonNull
    private Long grapeTypeId;

    @NonNull
    private Long providerId;
}
