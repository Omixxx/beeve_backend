package it.unimol.vino.controller;

import it.unimol.vino.controllers.ProviderController;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
import it.unimol.vino.services.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProviderControllerTest {

    private ProviderService providerService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        providerService = mock(ProviderService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProviderController(providerService)).build();
    }

    @Test
    void testGetAllProviders() throws Exception {
        Provider provider = new Provider();
        provider.setId(1L);
        provider.setName("Provider 1");
        when(providerService.getAll()).thenReturn(Collections.singletonList(provider));

        mockMvc.perform(get("/api/v1/provider/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Provider 1"));
    }

    @Test
    void testGetAllProvidedItemsById() throws Exception {
        ItemsProvidedByProvider itemsProvidedByProvider = new ItemsProvidedByProvider();
        itemsProvidedByProvider.setItemName("Item 1");
        when(providerService.getAllProvidedItemsById(1L)).thenReturn(Collections.singletonList(itemsProvidedByProvider));

        mockMvc.perform(get("/api/v1/provider/providedBy/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemName").value("Item 1"));
    }

    @Test
    void testGetProviderBook() throws Exception {
        ProviderBookResponse providerBookResponse = new ProviderBookResponse();
        providerBookResponse.setItemName("Item 1");
        when(providerService.getProviderBook()).thenReturn(Collections.singletonList(providerBookResponse));

        mockMvc.perform(get("/api/v1/provider/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemName").value("Item 1"));
    }

    @Test
    void testRegister() throws Exception {
        RegisterProviderRequest registerProviderRequest = new RegisterProviderRequest();
        registerProviderRequest.setName("Provider 1");
        when(providerService.providerRegister(registerProviderRequest)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Provider 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));
    }
    @Test
    void testRegisterThrowsUserAlreadyRegistered() throws Exception {
        RegisterProviderRequest registerProviderRequest = new RegisterProviderRequest();
        registerProviderRequest.setName("Provider 1");
        when(providerService.providerRegister(any())).thenThrow(new UserAlreadyRegistered());

        mockMvc.perform(post("/api/v1/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Provider 1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value("User already registered"));
    }

