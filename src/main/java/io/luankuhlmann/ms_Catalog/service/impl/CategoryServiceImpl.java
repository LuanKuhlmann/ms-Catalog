package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.CategoryRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.exception.EntityNotFoundException;
import io.luankuhlmann.ms_Catalog.exception.InvalidProductDataException;
import io.luankuhlmann.ms_Catalog.exception.ListIsEmptyException;
import io.luankuhlmann.ms_Catalog.mapper.CategoryMapper;
import io.luankuhlmann.ms_Catalog.mapper.CustomProductMapper;
import io.luankuhlmann.ms_Catalog.model.Category;
import io.luankuhlmann.ms_Catalog.repository.CategoryRepository;
import io.luankuhlmann.ms_Catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity createCategory(CategoryRequestDTO categoryRequestDTO) {
        validateCategoryDTO(categoryRequestDTO);

        Category category = categoryMapper.toEntity(categoryRequestDTO);

        if (categoryRequestDTO.parentId() != null) {
            Category parent = categoryRepository.findById(categoryRequestDTO.parentId()).orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            category.setParent(parent);
        }

        categoryRepository.save(category);

        return ResponseEntity.ok().build();
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return buildCategoryTree(categories);
    }

    public List<ProductResponseDTO> getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<ProductResponseDTO> productList = category.getProducts().stream()
                .map(customProductMapper::mapToResponseDTO)
                .toList();

        if (productList.isEmpty()) {
            throw new ListIsEmptyException("No products was found for this category");
        }

        return productList;

        /*return category.getProducts().stream()
                .map(customProductMapper::mapToResponseDTO)
                .collect(Collectors.toList());*/
    }

    public ResponseEntity updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        validateCategoryDTO(categoryRequestDTO);

        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        existingCategory.setName(categoryRequestDTO.name());
        existingCategory.setActive(categoryRequestDTO.active());

        if (categoryRequestDTO.parentId() != null) {
            Category parent = categoryRepository.findById(categoryRequestDTO.parentId()).orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            existingCategory.setParent(parent);
        }

        categoryRepository.save(existingCategory);
        return ResponseEntity.ok().build();
    }

    @Override
    public void deactivateCategoryAndChildren(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setActive(false);
        for (Category child : category.getChildren()) {
            deactivateCategoryAndChildren(child.getId());
        }
        categoryRepository.save(category);
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