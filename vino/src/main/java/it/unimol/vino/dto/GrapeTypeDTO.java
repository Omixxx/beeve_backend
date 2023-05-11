package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.GrapeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrapeTypeDTO {

    private String type;

    private String color;

    private String species;

    public static GrapeTypeDTO getFullGrapeTypeDTO(@NotNull GrapeType grapeType){
        return GrapeTypeDTO.builder()
                .type(grapeType.getType())
                .color(grapeType.getColor())
                .species(grapeType.getSpecies())
                .build();
    }

    public static GrapeTypeDTO getOnlyIDGrapeType(@NotNull GrapeType grapeType){
        return GrapeTypeDTO.builder()
                .type(grapeType.getType())
                .build();
    }
}
