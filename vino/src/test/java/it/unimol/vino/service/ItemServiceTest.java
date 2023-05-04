package it.unimol.vino.service;

import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.ProviderSupplyItem;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.Date;
import java.util.List;
import java.util.Optional;



public class ItemServiceTest {

    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ProviderRepository providerRepository;

    private Provider provider;
    private RegisterItemRequest validRequest;
    private RegisterItemRequest invalidRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.itemService = new ItemService(itemRepository, providerRepository);

        // Setup provider
        this.provider = Provider.builder()
                .id(1L)
                .name("Provider Test")
                .address("Test address")
                .email("test@test.com")
                .phone_number("1234567890")
                .build();

        // Setup valid request
        ProviderSupplyItem validProviderSupplyItem = ProviderSupplyItem.builder()
                .quantity(10L)
                .date(new Date())
                .build();

        this.validRequest = RegisterItemRequest.builder()
                .capacity(100L)
                .description("Test Item")
                .provider_id(1L)
                .providerSupplyItem(validProviderSupplyItem)
                .build();

        // Setup invalid request (provider not found)
        ProviderSupplyItem invalidProviderSupplyItem = ProviderSupplyItem.builder()
                .quantity(10L)
                .date(new Date())
                .build();

        this.invalidRequest = RegisterItemRequest.builder()
                .capacity(100L)
                .description("Test Item")
                .provider_id(2L) // non-existing provider ID
                .providerSupplyItem(invalidProviderSupplyItem)
                .build();
    }

    @Test
    public void testGetItemWithId() {
        Long id = 1L;
        Item expectedItem = Item.builder().id(id).build();

        Mockito.when(itemRepository.findAllById(id)).thenReturn(List.of(expectedItem));

        List<Item> items = itemService.getItem(id);

        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals(expectedItem, items.get(0));
    }

    @Test
    public void testGetAllItems() {
        Item item1 = Item.builder().id(1L).build();
        Item item2 = Item.builder().id(2L).build();

        Mockito.when(itemRepository.findAll()).thenReturn(List.of(item1, item2));

        List<Item> items = itemService.getItem(null);

        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals(item1, items.get(0));
        Assertions.assertEquals(item2, items.get(1));
    }

    @Test
    public void testItemRegisterWithValidRequest() {
        Mockito.when(providerRepository.findById(validRequest.getProvider_id())).thenReturn(Optional.of(provider));
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(Item.builder().id(1L).build());

        Long itemId = itemService.itemRegister(validRequest);

        Assertions.assertEquals(1L, itemId);
    }

}





