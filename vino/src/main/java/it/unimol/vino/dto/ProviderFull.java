package it.unimol.vino.dto;


import java.util.List;

public record ProviderFull(Long id,
                           String name,
                           String phone_number,
                           String email,

                           List<ItemDTO> items
) {
}
