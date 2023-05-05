package it.unimol.vino.entity;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.ProviderSupplyItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemEntityTest {

    private Item item;
    private Provider provider;
    private List<ProviderSupplyItem> providerSupplyItemList;

    @BeforeEach
    public void setUp() {
        item = new Item();
        item.setId(1L);
        item.setCapacity(10L);
        item.setDescription("Test item description");

        provider = new Provider();
        provider.setId(1L);
        provider.setName("Test provider name");

        providerSupplyItemList = new ArrayList<>();
    }

    @Test
    public void testAddProviderMapping() {
        Integer quantity = 5;
        Date date = new Date();

        item.setProviderSupplyItemList(providerSupplyItemList);

        item.addProviderMapping(provider, quantity, date);

        Assertions.assertEquals(1, item.getProviderSupplyItemList().size());

        ProviderSupplyItem providerSupplyItem = item.getProviderSupplyItemList().get(0);

        Assertions.assertEquals(quantity, providerSupplyItem.getQuantity());
        Assertions.assertEquals(date, providerSupplyItem.getDate());
        Assertions.assertEquals(provider, providerSupplyItem.getProvider());
        Assertions.assertEquals(item, providerSupplyItem.getItem());
    }

}

