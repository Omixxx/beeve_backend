package it.unimol.vino.controllers;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.dto.ItemCategoryDTO;
import it.unimol.vino.dto.ItemDTO;
import it.unimol.vino.models.entity.Item;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.DecreaseTotalQuantityOfItemRequest;
import it.unimol.vino.models.request.RegisterItemRequest;
import it.unimol.vino.services.ItemService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
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
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.MAGAZZINO)
    public ResponseEntity<List<ItemCategoryDTO>> getAll(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all items");
        return ResponseEntity.ok(this.itemService.getAll());
    }

    @GetMapping("/{categoryName}")
    @RequirePermissions(value = {PermissionType.READ}, sector = SectorName.MAGAZZINO)
    public ResponseEntity<List<ItemDTO>> getItem(@Valid @PathVariable String categoryName, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all items of a category");
        return ResponseEntity.ok(this.itemService.getItems(categoryName));
    }

    @PostMapping
    @RequirePermissions(value = {PermissionType.WRITE}, sector = SectorName.MAGAZZINO)
    public ResponseEntity<String> register(@RequestBody @Valid RegisterItemRequest registerItemRequest, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is registering a new item");
        Item item = this.itemService.itemRegister(registerItemRequest);
        return ResponseEntity.ok("L'item " + item.getName() + " è stato registrato con successo");
    }

    @PostMapping("/decrease")
    @RequirePermissions(value = {PermissionType.UPDATE}, sector = SectorName.MAGAZZINO)
    public ResponseEntity<String> decreaseTotalQuantityOfItem(
            @RequestBody @Valid DecreaseTotalQuantityOfItemRequest request) {
        return ResponseEntity.ok(this.itemService.decreaseTotalQuantityOfItem(request));
    }

    @DeleteMapping("/delete/{id}")
    @RequirePermissions(value = {PermissionType.DELETE}, sector = SectorName.MAGAZZINO)
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is deleting an item");
        return ResponseEntity.ok(this.itemService.deleteItem(id));
    }

}
