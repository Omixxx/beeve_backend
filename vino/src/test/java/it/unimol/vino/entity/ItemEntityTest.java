package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.ProviderSupplyItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ItemEntityTest{

    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item();
    }

    @Test
    public void testAddMapping() {
        Provider provider = new Provider();
        ProviderSupplyItem providerSupplyItem = ProviderSupplyItem.builder()
                .quantity(10L)
                .date(new Date())
                .build();

        item.addMapping(provider, providerSupplyItem);

        Assertions.assertNotNull(item.getProviderSupplyItemList());
        Assertions.assertEquals(1, item.getProviderSupplyItemList().size());

        ProviderSupplyItem result = item.getProviderSupplyItemList().get(0);
        Assertions.assertEquals(10L, result.getQuantity());
        Assertions.assertEquals(provider, result.getProvider());
        Assertions.assertEquals(item, result.getItem());
    }

}
