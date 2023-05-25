package it.unimol.vino.controllers;

import it.unimol.vino.dto.ItemCategoryDTO;
import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.exceptions.CategoryNotFoundException;
import it.unimol.vino.exceptions.ItemNotFoundException;
import it.unimol.vino.exceptions.UserAlreadyRegistered;

import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.request.DecreaseTotalQuantityOfItemRequest;
import it.unimol.vino.models.request.RegisterItemRequest;

import it.unimol.vino.services.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<ItemCategoryDTO>> getAll(){
        return ResponseEntity.ok(this.itemService.getAll());
    }
    @GetMapping("/{categoryName}")
    public ResponseEntity<List<ItemDTO>> getItem(@Valid @PathVariable String categoryName) {
        return ResponseEntity.ok(this.itemService.getItems(categoryName));
    }

    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterItemRequest registerItemRequest) {
        Item item = this.itemService.itemRegister(registerItemRequest);
        return ResponseEntity.ok("L'item " + item.getName() + " è stato registrato con successo");
    }

    @PostMapping("/decrease")
    public ResponseEntity<String> decreaseTotalQuantityOfItem(
            @RequestBody @Valid DecreaseTotalQuantityOfItemRequest request) {
        return ResponseEntity.ok(this.itemService.decreaseTotalQuantityOfItem(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.itemService.deleteItem(id));
    }

}
