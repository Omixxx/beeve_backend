package it.unimol.vino.controllers;

import it.unimol.vino.dto.CategoryDTO;
import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.request.CategoryRequest;
import it.unimol.vino.services.CategoryService;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategory(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all categories");
        return ResponseEntity.ok(this.service.getAllCategory());

    }

    @PostMapping
    public ResponseEntity<String> postCategory(
            @Valid @RequestBody CategoryRequest request,
            HttpServletRequest servletRequest
    ) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting to create a new category");
        Category postedCategory = this.service.postCategory(request);
        return ResponseEntity.ok("Categoria " +
                postedCategory.getName() + " creata con successo"
        );
    }

}
