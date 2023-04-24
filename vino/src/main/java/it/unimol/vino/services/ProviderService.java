package it.unimol.vino.services;

import it.unimol.vino.exceptions.UserAlreadyRegistered;

import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.RegisterProviderRequest;

import it.unimol.vino.repository.ProviderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAll() {
        return this.providerRepository.findAll();
    }

    public Long providerRegister(@Valid RegisterProviderRequest request) {

        if (this.providerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyRegistered("Email already in use");
        }

        var provider = Provider.builder()
                .name(request.getName())
                .phone_number(request.getPhone_number())
                .email(request.getEmail())
                .address(request.getAddress())
                .website_url(request.getWebsite_url())
                .build();

        return this.providerRepository.save(provider).getId();

    }



}
