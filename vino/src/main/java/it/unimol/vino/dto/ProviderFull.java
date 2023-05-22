package it.unimol.vino.dto;


import it.unimol.vino.models.entity.Item;

import java.util.List;

public record ProviderFull (Long id,
                            String name,
                            String phone_number,
                            String email,

                            List<ItemDTO> items
                                        )
{

}
