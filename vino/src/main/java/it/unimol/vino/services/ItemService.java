package it.unimol.vino.services;

import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.entity.ProviderSupplyItem;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProviderRepository providerRepository;


    public ItemService(ItemRepository itemRepository, ProviderRepository providerRepository) {
        this.itemRepository = itemRepository;
        this.providerRepository = providerRepository;
    }

    public List<Item> getItem(Long id){
        if(id != null){
            return this.itemRepository.findAllById(id);
        } else {
            return this.itemRepository.findAll();
        }
    }

    @Transactional
    public Long itemRegister(@Valid RegisterItemRequest request) {

        Long provider_id = request.getProvider_id();
        Provider provider = this.providerRepository.findById(provider_id).orElseThrow(
                () -> new ProviderNotFoundException("Il provider con ID " + provider_id + " non è stato trovato")
        );


        var item = Item.builder()
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .build();

        item.addMapping(provider,request.getProviderSupplyItem());

        return this.itemRepository.save(item).getId();

    }
}
