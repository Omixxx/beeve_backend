package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.models.entity.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContributionDTO {

    private Long id;


    private String origin;


    private String country;


    private byte[] image;


    private String description;


    private Double sugarDegree;


    private Double quantity;


    private Date date;


    private GrapeTypeDTO associatedGrapeType;


    private ProviderDTO provider;
}
