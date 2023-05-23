package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.GrapeType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrapeTypeDTO {
    private Long id;

    private String species;

    private String color;

    public static GrapeTypeDTO getFullGrapeTypeDTO(@NotNull GrapeType grapeType) {
        return GrapeTypeDTO.builder()
                .id(grapeType.getId())
                .species(grapeType.getSpecies())
                .color(grapeType.getColor())
                .build();
    }
}
