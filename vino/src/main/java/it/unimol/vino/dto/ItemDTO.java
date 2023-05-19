package it.unimol.vino.dto;

public record ItemDTO (
        Long id,
        String name,
        Long capacity,
        String description,
        Integer totQuantity
)

{
}
