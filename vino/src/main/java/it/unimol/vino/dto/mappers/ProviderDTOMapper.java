package it.unimol.vino.dto.mappers;

import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.response.ProviderBookResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProviderDTOMapper implements Function<Provider, ProviderDTO> {

    @Override
    public ProviderDTO apply(Provider provider) {
        return new ProviderDTO(
                provider.getId(),
                provider.getName(),
                provider.getPhone_number(),
                provider.getEmail()
        );
    }



}
