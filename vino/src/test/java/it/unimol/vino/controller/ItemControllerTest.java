
package it.unimol.vino.controller;

import it.unimol.vino.controllers.ItemController;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.services.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Captor
    private ArgumentCaptor<RegisterItemRequest> requestCaptor;

    @Test
    void getItem_shouldReturnAllItems() {
        // Arrange
        List<Item> expectedItems = Arrays.asList(
                new Item(1L, 500L, "Description 1", Collections.emptyList()),
                new Item(2L, 750L, "Description 2", Collections.emptyList())
        );
        when(itemService.getItem(null)).thenReturn(expectedItems);

        // Act
        ResponseEntity<List<Item>> response = itemController.getItem(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedItems, response.getBody());
    }

    @Test
    void getItem_shouldReturnItemById() {
        // Arrange
        long id = 1L;
        Item expectedItem = new Item(id, 500L, "Description 1", Collections.emptyList());
        when(itemService.getItem(id)).thenReturn(Collections.singletonList(expectedItem));

        // Act
        ResponseEntity<List<Item>> response = itemController.getItem(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(expectedItem), response.getBody());
    }



    @Test
    public void getItem_shouldReturnAllItems_whenNoIdIsProvided() {
        // Arrange
        Item item1 = new Item();
        item1.setId(1L);
        Item item2 = new Item();
        item2.setId(2L);
        List<Item> items = Arrays.asList(item1, item2);
        when(itemService.getItem(null)).thenReturn(items);

        // Act
        ResponseEntity<List<Item>> response = itemController.getItem(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    public void getItem_shouldReturnOneItem_whenIdIsProvided() {
        // Arrange
        Item item = new Item();
        item.setId(1L);
        when(itemService.getItem(1L)).thenReturn(Arrays.asList(item));

        // Act
        ResponseEntity<List<Item>> response = itemController.getItem(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(item), response.getBody());
    }

}




