package it.unimol.vino.services;

import it.unimol.vino.dto.ItemCategoryDTO;
import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.dto.mappers.ItemCategoryDTOMapper;
import it.unimol.vino.dto.mappers.ItemDTOMapper;
import it.unimol.vino.exceptions.CapacityEqualToZeroInAPrimaryItemNotAllowedException;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.ProviderNotFoundException;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.request.DecreaseTotalQuantityOfItemRequest;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.repository.CategoryRepository;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProviderRepository providerRepository;
    private final CategoryRepository categoryRepository;
    private final ItemDTOMapper itemDTOMapper;
    private final ItemCategoryDTOMapper itemCategoryDTOMapper;

    public List<ItemCategoryDTO> getAll() {
        List<Category> categories = this.categoryRepository.findAll();
        List<ItemCategoryDTO> items = new ArrayList<>();
        for (Category category : categories) {
            items.addAll(this.itemRepository.findAllByCategory(category).stream().map(itemCategoryDTOMapper).toList());
        }
        return items;
    }

    public List<ItemDTO> getItems(String categoryName) {
        Category category = findCategory(categoryName);
        return this.itemRepository.findAllByCategory(category).stream().map(itemDTOMapper).toList();
    }

    @Transactional
    public Item itemRegister(@Valid RegisterItemRequest request) {

        Category category = findCategory(request.getCategoryName());
        Provider provider = findProvider(request.getProvider_id());

        if (request.getCapacity() == 0 && category.getIsPrimary()) {
            throw new CapacityEqualToZeroInAPrimaryItemNotAllowedException
                    ("La quantità per un item appartenente a una categoria primaria "
                            + "deve essere maggiore di 0"
                    );
        }


        List<Item> items = this.itemRepository.findAll().stream().filter(
                item -> item.getCategory().equals(category)
                        && item.getCapacity().equals(request.getCapacity())
                        && item.getName().equals(request.getName().toUpperCase())
        ).toList();


        if (!items.isEmpty()) {
            Item existingItem = items.get(0);
            existingItem.addQuantity(request.getQuantity());
            existingItem.addProviderMapping(provider, request.getQuantity(), request.getDate());
            return this.itemRepository.save(existingItem);
        }

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
        return newItem;
    }

    @Transactional
    public String deleteItem(Long item_id) {
        //non funziona
        Item item = this.itemRepository.findById(item_id).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + item_id + " non è stato trovato")
        );

        this.itemRepository.delete(item);
        return "Item eliminato con successo";
    }

    @Transactional
    public String decreaseTotalQuantityOfItem(@Valid DecreaseTotalQuantityOfItemRequest request) {


        Item item = this.itemRepository.findById(request.getId()).orElseThrow(
                () -> new ItemNotFoundException("L'item con ID " + request.getId() + " non è stato trovato")
        );

        item.decreaseQuantity(request.getQuantity());
        return request.getQuantity() + " unità sono state rimosse dall'item con ID "
                + request.getId() + "con successo";
    }


    public Category findCategory(String name) {
        return this.categoryRepository.findByName(name.toUpperCase()).orElseThrow(
                () -> new CategoryNotFoundException("La categoria con nome: " + name + " non è stata trovata"));
    }

    public Provider findProvider(Long provider_id) {
        return this.providerRepository.findById(provider_id).orElseThrow(
                () -> new ProviderNotFoundException("IL provider con ID " + provider_id + " non è stato trovato")
        );
    }
}