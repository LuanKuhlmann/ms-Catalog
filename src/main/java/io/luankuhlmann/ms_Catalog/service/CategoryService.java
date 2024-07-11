package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.CategoryRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    ResponseEntity createCategory(CategoryRequestDTO categoryRequestDTO);

    List<CategoryResponseDTO> getAllCategories();

    List<ProductResponseDTO> getProductsByCategory(Long id);

    ResponseEntity updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    void deactivateCategoryAndChildren(Long id);
}
