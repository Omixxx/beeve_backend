package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.Category;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDTO(
        Long id,
        String name,
        Float capacity,
        String description,
        Integer totQuantity
) {
}
