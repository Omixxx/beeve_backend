package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemCategoryDTO(
        Long id,
        String name,
        Float capacity,
        Integer usedQuantity,
        String category,
        Boolean categoryIsPrimary
) {
}