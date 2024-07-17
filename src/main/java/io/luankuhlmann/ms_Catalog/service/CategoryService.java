package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.CategoryRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    ResponseEntity<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO);

    ResponseEntity<List<CategoryResponseDTO>> getAllCategories();

    ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(Long id);

    ResponseEntity<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    ResponseEntity<CategoryResponseDTO> deactivateCategoryAndChildren(Long id);
}
