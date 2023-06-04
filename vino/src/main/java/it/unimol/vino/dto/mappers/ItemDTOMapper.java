package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.models.entity.Item;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ItemDTOMapper implements Function<Item, ItemDTO> {
    @Override
    public ItemDTO apply(Item item) {
        return new ItemDTO(item.getId(),
                item.getName(),
                item.getCapacity(),
                item.getDescription(),
                item.getTotQuantity());
    }
}
