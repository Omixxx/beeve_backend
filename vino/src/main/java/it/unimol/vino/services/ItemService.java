package it.unimol.vino.services;

import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProviderRepository providerRepository;
    private final CategoryRepository categoryRepository;


    public Item getItem(Long id) {
        return this.itemRepository.findById(id).orElse(null);
    }

    public List<Item> getItems() {
        return this.itemRepository.findAll();
    }

    @Transactional
    public String itemRegister(@Valid RegisterItemRequest request) {

        Long provider_id = request.getProvider_id();

        Provider provider = this.providerRepository.findById(provider_id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + provider_id + " non è stato trovato")
        );
        Category category = this.categoryRepository.findByName(request.getCategoryName()).orElseThrow(
                () -> new CategoryNotFoundException("La categoria con nome: " + request.getCategoryName() + " non è stata trovata")
        );


        var item = Item.builder()
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .providerSupplyItemList(new ArrayList<>())
                .category(category)
                .build();

        item.addProviderMapping(provider, request.getQuantity(), request.getDate());
        category.addItem(item);
        this.itemRepository.save(item);
        item.setQuantity(request.getQuantity());
        return "Registrato";

    }

    @Transactional
    public String deleteItem(Long item_id) {
        Item item = this.itemRepository.findById(item_id).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + item_id + " non è stato trovato")
        );

        this.itemRepository.delete(item);
        return "Item è stato eliminato con successo";
    }
}
