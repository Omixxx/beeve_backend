package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDTO(
        Long id,
        String name,
        Long capacity,
        String description,
        Integer totQuantity
) {
}
