package it.unimol.vino.services;

import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.MappingItemRequest;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public Item getItem(Long id){
            return this.itemRepository.findById(id).orElse(null);
    }

    public  List<Item> getItems(){
        return this.itemRepository.findAll();
    }

    @Transactional
    public String itemRegister(@Valid RegisterItemRequest request) {

        Long provider_id = request.getProvider_id();

        Provider provider = this.providerRepository.findById(provider_id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + provider_id + " non è stato trovato")
        );



        var item = Item.builder()
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .providerSupplyItemList(new ArrayList<>())
                .build();

        item.addMapping(provider,request.getQuantity(),request.getDate());

        this.itemRepository.save(item);

         return "Registrato";

    }

    @Transactional
    public String deleteItem(Long item_id){
        Item item = this.itemRepository.findById(item_id).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + item_id + " non è stato trovato")
        );

        this.itemRepository.delete(item);
        return "Item è stato eliminato con successo";
    }
}
