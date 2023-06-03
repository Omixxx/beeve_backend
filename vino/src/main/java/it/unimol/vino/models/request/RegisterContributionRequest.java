package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterContributionRequest {

    @NotNull(message = "E' necessario inserire l'origine del conferimento")
    private String origin;

    @NotNull(message = "E' necessario inserire il paese di origine del conferimento")
    private String country;

    private String photoURL;

    private String description;

    @Positive(message = "Il grado zuccherino deve essere maggiore di 0")
    private double sugarDegree;

    @Positive(message = "La quantità deve essere maggiore di 0")
    private double quantity;

    @NotNull(message = "E' necessario inserire la data del conferimento")
    private Date date;

    @NotNull(message = "E' necessario inserire l'id del tipo di uva")
    private Long grapeTypeId;

    @NotNull(message = "E' necessario inserire l'id del fornitore")
    private Long providerId;
}
