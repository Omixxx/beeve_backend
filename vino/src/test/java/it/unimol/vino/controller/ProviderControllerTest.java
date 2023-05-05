/*package it.unimol.vino.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimol.vino.controllers.ProviderController;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
import it.unimol.vino.services.ProviderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProviderController.class)
@ContextConfiguration(classes = { ProviderController.class })
class ProviderControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProviderService providerService;

    private final ProviderController providerController = new ProviderController(providerService);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(providerController).build();

    @Test
    void testGetAllProviders() throws Exception {
        // Setup mock service
        Provider provider = Provider.builder()
                .name("John")
                .phone_number("123456789")
                .email("john.doe@example.com")
                .address("123 Main St")
                .website_url("http://www.example.com")
                .build();
        List<Provider> providers = Collections.singletonList(provider);
        doReturn(providers).when(providerService).getAll();

        // Perform GET request
        mockMvc.perform(get("/api/v1/provider"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(provider.getName()))
                .andExpect(jsonPath("$[0].phone_number").value(provider.getPhone_number()))
                .andExpect(jsonPath("$[0].email").value(provider.getEmail()))
                .andExpect(jsonPath("$[0].address").value(provider.getAddress()))
                .andExpect(jsonPath("$[0].website_url").value(provider.getWebsite_url()));

        // Verify mock service was called
        verify(providerService).getAll();
        verifyNoMoreInteractions(providerService);
    }

    @Test
    void testGetAllProvidedItemsById() throws Exception {
        // Setup mock service
        ItemsProvidedByProvider item = ItemsProvidedByProvider.builder()
                .id(1L)
                .capacity(100L)
                .description("nuovo")
                .quantity(10)
                .date(new Date())
                .build();
        List<ItemsProvidedByProvider> items = Collections.singletonList(item);
        doReturn(items).when(providerService).getAllProvidedItemsById(anyLong());

        // Perform GET request
        Long providerId = 1L;
        mockMvc.perform(get("/api/v1/provider/providedBy/{id}", providerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantity").value(item.getQuantity()));

        // Verify mock service was called
        verify(providerService).getAllProvidedItemsById(providerId);
        verifyNoMoreInteractions(providerService);
    }


    @Test
    void testGetProviderBook() throws Exception {
// Setup mock service
        ProviderBookResponse providerBookResponse = new ProviderBookResponse(1L,"John", "Doe", "Wine");
        List<ProviderBookResponse> providerBookResponses = Collections.singletonList(providerBookResponse);
        Mockito.when(providerService.getProviderBook()).thenReturn(providerBookResponses);
        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/provider/book")
                        .contentType(MediaType.APPLICATION_JSON))
                // Verify response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName", Matchers.is("Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].item", Matchers.is("Wine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity", Matchers.is(1L)));
    }
}*/

