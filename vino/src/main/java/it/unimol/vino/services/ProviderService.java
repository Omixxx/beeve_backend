package it.unimol.vino.services;

import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.dto.mappers.ProviderDTOMapper;
import it.unimol.vino.dto.ProviderFull;
import it.unimol.vino.dto.mappers.ProviderFullDTOMapper;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;

import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.RegisterProviderRequest;

import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
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

    private final ProviderFullDTOMapper providerFullDTOMapper;


    public List<Provider> getAll() {
        return this.providerRepository.findAll();
    }

    public Long providerRegister(@Valid RegisterProviderRequest request) {

        if (this.providerRepository.findByName(request.getName()).isPresent()) {
            throw new UserAlreadyRegistered("Provider already in use");
        }

        Provider provider = Provider.builder()
                .name(request.getName())
                .phone_number(request.getPhone_number())
                .email(request.getEmail())
                .build();

        return this.providerRepository.save(provider).getId();

    }


    public List<ItemsProvidedByProvider> getAllProvidedItemsById(Long id) {
        //da eliminare

        this.providerRepository.findById(id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + id + " non è stato trovato")
        );

        return this.providerRepository.findProvidedItemsById(id);
    }

    public List<ProviderDTO> getProviderBook() {

        return this.providerRepository.findAll()
                .stream()
                .map(providerDTOMapper)
                .sorted(Comparator.comparing(ProviderDTO::name))
                .toList();

    }

    public List<ProviderFull> getFullProvides(){

       return this.providerRepository.findAll().stream()
                .map(providerFullDTOMapper)
                .toList();

    }

    public ProviderDTO getProviderByName(String name) {

       Provider provider= this.providerRepository.findByName(name).orElseThrow(
                ()-> new ProviderNotFoundException("Il provider con nome: "+name+"no è stato trovato")
        );

       return providerDTOMapper.apply(provider);
    }
}
