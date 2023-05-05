package it.unimol.vino.dto;

import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.response.ProviderBookResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProviderDTOMapper implements Function<Provider, ProviderBookResponse> {

    @Override
    public ProviderBookResponse apply(Provider provider) {
        return new ProviderBookResponse(
                provider.getId(),
                provider.getName(),
                provider.getPhone_number(),
                provider.getEmail()
        );
    }
}
