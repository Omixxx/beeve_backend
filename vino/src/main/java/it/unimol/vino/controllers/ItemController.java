package it.unimol.vino.controllers;


import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Item;

import it.unimol.vino.models.request.MappingItemRequest;
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

    @GetMapping("{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id){
        return  ResponseEntity.ok(this.itemService.getItem(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<Item>> getItem(){
        return  ResponseEntity.ok(this.itemService.getItems());
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterItemRequest registerItemRequest)
            throws UserAlreadyRegistered{

        return ResponseEntity.ok(this.itemService.itemRegister(registerItemRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)throws ItemNotFoundException {
        return ResponseEntity.ok(this.itemService.deleteItem(id));
    }

}
