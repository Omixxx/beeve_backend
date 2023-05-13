package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.dto.ProviderFull;
import it.unimol.vino.dto.mappers.ItemDTOMapper;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.ProviderSupplyItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ProviderFullDTOMapper implements Function<Provider, ProviderFull> {

    private final ItemDTOMapper itemDTOMapper;

    public ProviderFullDTOMapper(ItemDTOMapper itemDTOMapper) {
        this.itemDTOMapper = itemDTOMapper;
    }

    @Override
    public ProviderFull apply(Provider provider) {
        List<ProviderSupplyItem> supply = provider.getProviderSupplyItemList();
        List<ItemDTO> items= supply.stream()
                .map(ProviderSupplyItem::getItem)
                .map(itemDTOMapper)
                .toList();

        return  new ProviderFull(provider.getId(),
                provider.getName(),
                provider.getPhone_number(),
                provider.getEmail(),
                items
                );
    }
}
