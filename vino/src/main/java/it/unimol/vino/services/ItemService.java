package it.unimol.vino.services;

import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.dto.mappers.ItemDTOMapper;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;

import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.models.request.DecreaseTotalQuantityOfItemRequest;
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
    private final ItemDTOMapper itemDTOMapper;


    public List<ItemDTO> getItems(String categoryName) {
        Category category = findCategory(categoryName);
        return this.itemRepository.findAllByCategory(category).stream().map(itemDTOMapper).toList();
    }

    @Transactional
    public String itemRegister(@Valid RegisterItemRequest request) {

        Category category = findCategory(request.getCategoryName());
        Provider provider = findProvider(request.getProvider_id());

        Item existingItem = this.itemRepository.findByCategoryAndCapacityAndName(category, request.getCapacity(), request.getName().toUpperCase()).orElse(null);

        if (existingItem != null) {
            existingItem.addQuantity(request.getQuantity());
            existingItem.addProviderMapping(provider, request.getQuantity(), request.getDate());
            this.itemRepository.save(existingItem);
        } else {
            Item newItem = Item.builder()
                    .capacity(request.getCapacity())
                    .description(request.getDescription())
                    .providerSupplyItemList(new ArrayList<>())
                    .category(category)
                    .totQuantity(request.getQuantity())
                    .name(request.getName().toUpperCase())
                    .build();

            newItem.addProviderMapping(provider, request.getQuantity(), request.getDate());
            this.itemRepository.save(newItem);
            category.addItem(newItem);

        }


        return "Registrato";

    }

    @Transactional
    public String deleteItem(Long item_id) {
        //non funziona
        Item item = this.itemRepository.findById(item_id).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + item_id + " non è stato trovato")
        );

        this.itemRepository.delete(item);
        return "Item è stato eliminato con successo";
    }

    @Transactional
    public String decreaseTotalQuantityOfItem(@Valid DecreaseTotalQuantityOfItemRequest request) {


        Item item = this.itemRepository.findById(request.getId()).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + request.getId() + " non è stato trovato")
        );

        item.decreaseQuantity(request.getQuantity());

        return "ok";
    }

    public Item findItem(Category category, Long capacity, String name) {
        return this.itemRepository.findByCategoryAndCapacityAndName(category, capacity, name.toUpperCase()).orElseThrow(
                () -> new ItemNotFoundException("L'item  " + " non è stato trovato"));
    }

    public Category findCategory(String name) {
        return this.categoryRepository.findByName(name.toUpperCase()).orElseThrow(
                () -> new CategoryNotFoundException("La categoria con nome: " + name + " non è stata trovata"));
    }

    public Provider findProvider(Long provider_id){
        return this.providerRepository.findById(provider_id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + provider_id + " non è stato trovato")
        );
    }
}