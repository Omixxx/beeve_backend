package it.unimol.vino.service;

import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.RegisterProviderRequest;
import it.unimol.vino.repository.ProviderRepository;
import it.unimol.vino.services.ProviderService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Provider Name";
    private static final String PHONE_NUMBER = "1234567890";
    private static final String EMAIL = "email@example.com";


    private Validator validator;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testProviderRegisterThrowsUserAlreadyRegistered() {
        RegisterProviderRequest request = new RegisterProviderRequest();
        request.setName(NAME);
        request.setPhone_number(PHONE_NUMBER);
        request.setEmail(EMAIL);


        Provider existingProvider = new Provider();
        existingProvider.setId(ID);
        existingProvider.setName(NAME);
        existingProvider.setPhone_number(PHONE_NUMBER);
        existingProvider.setEmail(EMAIL);

        when(providerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(existingProvider));

        assertThrows(UserAlreadyRegistered.class, () -> providerService.providerRegister(request));

        verify(providerRepository).findByEmail(EMAIL);
        verify(providerRepository, never()).save(any(Provider.class));
    }



}


