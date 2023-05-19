package it.unimol.vino.dto;

import lombok.Builder;

@Builder
public record ItemDTO (
        Long id,
        String name,
        Long capacity,
        String description,
        Integer totQuantity
)

{
}
