package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ItemCategoryDTO;
import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.models.entity.Item;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ItemCategoryDTOMapper implements Function<Item, ItemCategoryDTO> {
    @Override
    public ItemCategoryDTO apply(Item item) {
        return new ItemCategoryDTO(item.getId(),
                item.getName(),
                item.getCapacity(),
                item.getTotQuantity(),
                item.getCategory().getName(),
                item.getCategory().getIsPrimary());
    }
}
