package it.unimol.vino.services;

import it.unimol.vino.dto.ProviderDTO;
import it.unimol.vino.dto.mappers.ProviderDTOMapper;
import it.unimol.vino.dto.ProviderFull;
import it.unimol.vino.dto.mappers.ProviderFullDTOMapper;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;

import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.RegisterProviderRequest;

import it.unimol.vino.models.request.UpdateProviderRequest;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import it.unimol.vino.models.response.ProviderBookResponse;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.transaction.Transactional;
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
        return this.providerRepository.findByIsVisible(true);
    }

    public Long providerRegister(@Valid RegisterProviderRequest request) {

        if (this.providerRepository.findByName(request.getName()).isPresent()) {
            throw new UserAlreadyRegistered("Fornitore con nome: "+request.getName()+" già presente");
        }

        if(this.providerRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyRegistered("Fornitore con email: "+request.getEmail()+" già presente");
        }

        Provider provider = Provider.builder()
                .name(request.getName())
                .phone_number(request.getPhone_number())
                .email(request.getEmail())
                .address(request.getAddress())
                .isVisible(true)
                .build();

        return this.providerRepository.save(provider).getId();

    }
    @Transactional
    public String updateProvider(UpdateProviderRequest request) {

        Provider provider = this.providerRepository.findByName(request.getOldName()).orElseThrow(
                ()-> new ProviderNotFoundException("Provider non trovato")
        );

        provider.setEmail(request.getEmail());
        provider.setName(request.getNewName());
        provider.setPhone_number(request.getPhone_number());
        provider.setAddress(request.getAddress());
        this.providerRepository.save(provider);


        return "Aggiornato";
    }



    public List<ProviderDTO> getProviderBook() {

        return this.providerRepository.findByIsVisible(true)
                .stream()
                .map(providerDTOMapper)
                .sorted(Comparator.comparing(ProviderDTO::name))
                .toList();

    }



    public ProviderDTO getProviderByName(String name) {

       Provider provider= this.providerRepository.findByName(name).orElseThrow(
                ()-> new ProviderNotFoundException("Il provider con nome: "+name+"no è stato trovato")
        );

       return providerDTOMapper.apply(provider);
    }

    public String changeVisibility(String name) {
        Provider provider = this.providerRepository.findByName(name).orElseThrow(
                ()-> new ProviderNotFoundException("Il provider con nome: "+name+" non è stato trovato")
        );
        provider.setIsVisible(false);
        this.providerRepository.save(provider);
        return "Provider rimosso dalla lista dei contatti";
    }
}
