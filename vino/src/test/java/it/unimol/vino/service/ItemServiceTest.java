/*package it.unimol.vino.service;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private CategoryRepository categoryRepository;
    private long capacity=100;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(itemRepository, providerRepository, categoryRepository);
    }

    @Test
    public void testGetItem() {
        Long itemId = 1L;
        Item item = Item.builder().id(itemId).build();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Item result = itemService.getItem(itemId);

        assertNotNull(result);
        assertEquals(itemId, result.getId());
        verify(itemRepository, times(1)).findById(itemId);
    }

    @Test
    public void testGetItems() {
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(itemList);

        List<Item> result = itemService.getItems();

        assertNotNull(result);
        assertEquals(itemList, result);
        verify(itemRepository, times(1)).findAll();
    }


    @Test
    public void testDeleteItem() {
        Long itemId = 1L;
        Item item = new Item();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        String result = itemService.deleteItem(itemId);
        assertEquals("Item è stato eliminato con successo", result);

        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    public void testDeleteItemNotFound() {
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(itemId));
        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, never()).delete(any(Item.class));
    }
}*/
