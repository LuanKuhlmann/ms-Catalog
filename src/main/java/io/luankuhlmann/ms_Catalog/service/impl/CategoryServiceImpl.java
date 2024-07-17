package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.CategoryRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.exception.CategoryAlreadyDeactivatedException;
import io.luankuhlmann.ms_Catalog.exception.EntityNotFoundException;
import io.luankuhlmann.ms_Catalog.exception.InvalidProductDataException;
import io.luankuhlmann.ms_Catalog.exception.ListIsEmptyException;
import io.luankuhlmann.ms_Catalog.mapper.CategoryMapper;
import io.luankuhlmann.ms_Catalog.mapper.CustomProductMapper;
import io.luankuhlmann.ms_Catalog.model.Category;
import io.luankuhlmann.ms_Catalog.repository.CategoryRepository;
import io.luankuhlmann.ms_Catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CustomProductMapper customProductMapper;

    public ResponseEntity<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {
        validateCategoryDTO(categoryRequestDTO);
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        if (categoryRequestDTO.parentId() != null) {
            Category parent = categoryRepository.findById(categoryRequestDTO.parentId()).orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            category.setParent(parent);
        }
        CategoryResponseDTO createdCategory = categoryMapper.toResponseDTO(categoryRepository.save(category));
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = buildCategoryTree(categoryRepository.findAll());
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        List<ProductResponseDTO> productList = category.getProducts().stream()
                .map(customProductMapper::mapToResponseDTO)
                .toList();
        if (productList.isEmpty()) {
            throw new ListIsEmptyException("No products was found for this category");
        }
        return ResponseEntity.ok(productList);
    }

    public ResponseEntity<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        validateCategoryDTO(categoryRequestDTO);
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        existingCategory.setName(categoryRequestDTO.name());
        existingCategory.setActive(categoryRequestDTO.active());
        if (categoryRequestDTO.parentId() != null) {
            Category parent = categoryRepository.findById(categoryRequestDTO.parentId()).orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            existingCategory.setParent(parent);
        }
        CategoryResponseDTO updatedCategory = categoryMapper.toResponseDTO(categoryRepository.save(existingCategory));
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponseDTO> deactivateCategoryAndChildren(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if(!category.getActive()) {
            throw new CategoryAlreadyDeactivatedException("This category is already inactive");
        }
        category.setActive(false);
        for (Category child : category.getChildren()) {
            deactivateCategoryAndChildren(child.getId());
        }
        CategoryResponseDTO deactivatedCategory = categoryMapper.toResponseDTO(categoryRepository.save(category));
        return new ResponseEntity<>(deactivatedCategory, HttpStatus.OK);
    }

    private void validateCategoryDTO(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRequestDTO.name() == null || categoryRequestDTO.active() == null) {
            throw new InvalidProductDataException("Invalid category data");
        }
    }

    private List<CategoryResponseDTO> buildCategoryTree(List<Category> categories) {
        Map<Long, CategoryResponseDTO> categoryMap = new HashMap<>();
        List<CategoryResponseDTO> roots = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponseDTO categoryDTO = new CategoryResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getActive(),
                    new ArrayList<>()
            );
            categoryMap.put(category.getId(), categoryDTO);

            if (category.getParent() == null) {
                roots.add(categoryDTO);
            } else {
                CategoryResponseDTO parentDTO = categoryMap.get(category.getParent().getId());
                if (parentDTO != null) {
                    parentDTO.children().add(categoryDTO);
                }
            }
        }
        if (roots.isEmpty()) {
            throw new ListIsEmptyException("No category was found");
        }
        return roots;
    }
}