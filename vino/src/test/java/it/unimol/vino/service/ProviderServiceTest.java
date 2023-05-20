package it.unimol.vino.service;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.services.ProviderService;
import it.unimol.vino.dto.ProviderDTOMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderServiceTest {
    private ProviderDTOMapper providerDTOMapper;

    private static final Long ID = 1L;
    private static final String NAME = "Provider Name";
    private static final String PHONE_NUMBER = "1234567890";
    private static final String EMAIL = "email@example.com";
    private static final String ADDRESS = "Provider Address";
    private static final String WEBSITE_URL = "http://www.provider.com";

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        providerDTOMapper = new ProviderDTOMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        providerService = new ProviderService(providerRepository, providerDTOMapper);
    }

    @Test
    void testGetAll() {
        List<Provider> expectedProviders = Collections.singletonList(new Provider());
        when(providerRepository.findAll()).thenReturn(expectedProviders);

        List<Provider> actualProviders = providerService.getAll();

        assertEquals(expectedProviders, actualProviders);
        verify(providerRepository).findAll();
    }


    @Test
    void testProviderRegisterThrowsUserAlreadyRegistered() {
        RegisterProviderRequest request = new RegisterProviderRequest();
        request.setName(NAME);
        request.setPhone_number(PHONE_NUMBER);
        request.setEmail(EMAIL);
        //request.setAddress(ADDRESS);
        //request.setWebsite_url(WEBSITE_URL);

        Provider existingProvider = new Provider();
        existingProvider.setId(ID);
        existingProvider.setName(NAME);
        existingProvider.setPhone_number(PHONE_NUMBER);
        existingProvider.setEmail(EMAIL);
       // existingProvider.setAddress(ADDRESS);
       // existingProvider.setWebsite_url(WEBSITE_URL);
        when(providerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(existingProvider));

        assertThrows(UserAlreadyRegistered.class, () -> providerService.providerRegister(request));

        verify(providerRepository).findByEmail(EMAIL);
        verify(providerRepository, never()).save(any(Provider.class));
    }

    @Test
    void testGetAllProvidedItemsById() {
        Long id = 1L;

        Provider provider = new Provider();
        provider.setId(id);

        ItemsProvidedByProvider expectedItems = new ItemsProvidedByProvider();
        when(providerRepository.findById(id)).thenReturn(Optional.of(provider));
        when(providerRepository.findProvidedItemsById(id)).thenReturn(Collections.singletonList(expectedItems));

        List<ItemsProvidedByProvider> actualItems = providerService.getAllProvidedItemsById(id);

        assertEquals(Collections.singletonList(expectedItems), actualItems);
        verify(providerRepository).findById(id);
        verify(providerRepository).findProvidedItemsById(id);
    }

    @Test
    void testGetAllProvidedItemsByIdThrowsProviderNotFoundException() {
        Long id = 1L;

        when(providerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class, () -> providerService.getAllProvidedItemsById(id));

        verify(providerRepository).findById(id);
        verify(providerRepository, never()).findProvidedItemsById(id);
    }
    @Test
    void testGetProviderBook() {

        List<Provider> providers = new ArrayList<>();
        providers.add(
                Provider.builder()
                        .id(1L)
                        .name("Provider A")
                        .phone_number("1234567890")
                        .email("providerA@example.com")
                        .build()
        );
        providers.add(
                Provider.builder()
                        .id(2L)
                        .name("Provider B")
                        .phone_number("0987654321")
                        .email("providerB@example.com")
                        .build()
        );


        when(providerRepository.findAll()).thenReturn(providers);

        List<ProviderBookResponse> expected = new ArrayList<>();
        expected.add(
                new ProviderBookResponse(
                        1L,
                        "Provider A",
                        "1234567890",
                        "providerA@example.com"
                )
        );
        expected.add(
                new ProviderBookResponse(
                        2L,
                        "Provider B",
                        "0987654321",
                        "providerB@example.com"
                )
        );


        List<ProviderBookResponse> actual = providerService.getProviderBook();


        assertEquals(expected, actual);
    }
}

