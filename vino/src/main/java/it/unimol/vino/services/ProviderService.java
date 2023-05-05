package it.unimol.vino.services;

import it.unimol.vino.dto.ProviderDTOMapper;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;

import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.RegisterProviderRequest;

import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
@AllArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderDTOMapper providerDTOMapper;


    public List<Provider> getAll() {
        return this.providerRepository.findAll();
    }

    public Long providerRegister(@Valid RegisterProviderRequest request) {

        if (this.providerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyRegistered("Email already in use");
        }

        Provider provider = Provider.builder()
                .name(request.getName())
                .phone_number(request.getPhone_number())
                .email(request.getEmail())
                .address(request.getAddress())
                .website_url(request.getWebsite_url())
                .build();

        return this.providerRepository.save(provider).getId();

    }


    public List<ItemsProvidedByProvider> getAllProvidedItemsById(Long id) {

        this.providerRepository.findById(id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + id + " non è stato trovato")
        );

        return this.providerRepository.findProvidedItemsById(id);
    }

    public List<ProviderBookResponse> getProviderBook() {

        return this.providerRepository.findAll()
                .stream()
                .map(providerDTOMapper)
                .sorted(Comparator.comparing(ProviderBookResponse::name))
                .toList();
    }
}
