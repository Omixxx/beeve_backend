package it.unimol.vino.controller;


import it.unimol.vino.controllers.ProviderController;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.services.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProviderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProviderService providerService;
    @InjectMocks

    private ProviderController providerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(providerController).build();
    }

    @Test
    void testGetAllProvidedItemsById() throws Exception {

        ItemsProvidedByProvider item = ItemsProvidedByProvider.builder()
                .id(1L)
                .capacity(100L)
                .description("nuovo")
                .quantity(10)
                .date(new Date())
                .build();
        List<ItemsProvidedByProvider> items = Collections.singletonList(item);
        doReturn(items).when(providerService).getAllProvidedItemsById(anyLong());


        Long providerId = 1L;
        mockMvc.perform(get("/api/v1/provider/providedBy/{id}", providerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantity").value(item.getQuantity()));


        verify(providerService).getAllProvidedItemsById(providerId);
        verifyNoMoreInteractions(providerService);
    }

}

