package it.unimol.vino.controllers;


import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;


import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.models.request.DecreaseTotalQuantityOfItemRequest;
import it.unimol.vino.models.request.RegisterItemRequest;

import it.unimol.vino.services.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Item>> getItem(@PathVariable CategoryRequest category){
        return  ResponseEntity.ok(this.itemService.getItems(category));
    }
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterItemRequest registerItemRequest)
            throws UserAlreadyRegistered, CategoryNotFoundException {

        return ResponseEntity.ok(this.itemService.itemRegister(registerItemRequest));
    }
    @PostMapping("/decrease")
    public ResponseEntity<String> decreseTotalQuantityOfItem(@RequestBody @Valid DecreaseTotalQuantityOfItemRequest request){
        return ResponseEntity.ok(this.itemService.decreaseTotalQuantityOfItem(request));
    }

    @DeleteMapping("/delete/{id}")
    //non funziona, il campo ID non esiste più, adattare in base alla nuova chiave composita.
    public ResponseEntity<String> delete(@PathVariable Long id)throws ItemNotFoundException {
        return ResponseEntity.ok(this.itemService.deleteItem(id));
    }


}
