package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ItemCategoryDTO;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.ProcessUseItem;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ItemProcessUseItemDTOMapper implements Function<ProcessUseItem, ItemCategoryDTO> {
    @Override
    public ItemCategoryDTO apply(ProcessUseItem processUseItem) {
        Item item = processUseItem.getItem();
        return new ItemCategoryDTO(item.getId(),
                item.getName(),
                item.getCapacity(),
                item.getTotQuantity(),
                item.getCategory().getName(),
                item.getCategory().getIsPrimary());
    }
}
