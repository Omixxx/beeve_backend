package it.unimol.vino.controllers;


import it.unimol.vino.exceptions.UserAlreadyRegistered;
import it.unimol.vino.models.entity.Item;

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

    @GetMapping(value = {"/","/{id}"})
    public ResponseEntity<List<Item>> getItem(@PathVariable(required = false)Long id){
        return   ResponseEntity.ok(this.itemService.getItem(id));
    }
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterItemRequest registerItemRequest)
            throws UserAlreadyRegistered{

        return ResponseEntity.ok(this.itemService.itemRegister(registerItemRequest));
    }




}
