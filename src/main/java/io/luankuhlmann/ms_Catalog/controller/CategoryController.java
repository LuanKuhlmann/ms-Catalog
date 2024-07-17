package io.luankuhlmann.ms_Catalog.controller;

import io.luankuhlmann.ms_Catalog.dto.request.CategoryRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO data) {
        return categoryService.createCategory(data);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long id) {
        return categoryService.getProductsByCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.updateCategory(id, categoryRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable Long id) {
        return categoryService.deactivateCategoryAndChildren(id);
    }
}