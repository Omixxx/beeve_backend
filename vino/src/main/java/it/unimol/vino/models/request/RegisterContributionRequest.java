package it.unimol.vino.models.request;

import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;

    private String description;

    @Positive
    private String sugarDegree;

    @Positive
    private String quantity;

    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @NonNull
    private String grapeTypeId;

    @NonNull
    private String providerId;
}
