package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Date;
@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContributionDTO {

    private Long id;


    private String origin;


    private String country;


    private String photoURL;


    private String description;


    private Double sugarDegree;


    private Double quantity;


    private Date date;


    private GrapeTypeDTO associatedGrapeType;


    private ProviderDTO provider;
}
