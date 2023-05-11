package it.unimol.vino.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.controllers.ItemController;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        mockMvc.perform(get("/api/v1/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    public void testDeleteItem() throws Exception {
        when(itemService.deleteItem(1L)).thenReturn("Item deleted successfully");

        mockMvc.perform(delete("/api/v1/item/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully"));
    }/*

    @Test
    public void testGetItems() throws Exception {
        Item item1 = new Item();
        item1.setId(1L);


        Item item2 = new Item();
        item2.setId(2L);


        List<Item> itemList = Arrays.asList(item1, item2);

        when(itemService.getItems()).thenReturn(itemList);

        mockMvc.perform(get("/api/v1/item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

    }

    @Test
    public void testRegisterItem() throws Exception {
        RegisterItemRequest registerItemRequest = new RegisterItemRequest();
        registerItemRequest.setDescription("botte");

        when(itemService.itemRegister(any())).thenReturn("Item registered successfully");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(registerItemRequest);

        mockMvc.perform(post("/api/v1/item/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Item registered successfully"));
    }*/



}
