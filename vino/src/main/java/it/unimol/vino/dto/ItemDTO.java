package it.unimol.vino.dto;

import lombok.Builder;

@Builder
public record ItemDTO(String name,
                      Long capacity,

                      String description,

                      Integer totQuantity
) {
}
