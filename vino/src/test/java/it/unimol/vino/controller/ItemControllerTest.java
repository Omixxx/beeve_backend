package it.unimol.vino.controller;

import it.unimol.vino.controllers.ItemController;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemController(itemService)).build();
    }

    @Test
    public void testGetItem() throws Exception {
        Item item = new Item();
        item.setDescription("bottiglia");
        item.setId(1L);

        when(itemService.getItem(1L)).thenReturn(item);

        mockMvc.perform(get("/api/v1/item/1")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteItem() throws Exception {
        when(itemService.deleteItem(1L)).thenReturn("Item deleted successfully");

        mockMvc.perform(delete("/api/v1/item/delete/1")).andExpect(status().isOk()).andExpect(content().string("Item deleted successfully"));
    }

}
