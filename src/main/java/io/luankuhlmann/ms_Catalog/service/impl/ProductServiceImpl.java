package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.ProductRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.exception.EntityNotFoundException;
import io.luankuhlmann.ms_Catalog.exception.InvalidProductDataException;
import io.luankuhlmann.ms_Catalog.exception.ListIsEmptyException;
import io.luankuhlmann.ms_Catalog.mapper.CustomProductMapper;
import io.luankuhlmann.ms_Catalog.mapper.ProductMapper;
import io.luankuhlmann.ms_Catalog.model.Category;
import io.luankuhlmann.ms_Catalog.model.Product;
import io.luankuhlmann.ms_Catalog.repository.CategoryRepository;
import io.luankuhlmann.ms_Catalog.repository.ProductRepository;
import io.luankuhlmann.ms_Catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CustomProductMapper customProductMapper;

    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO) {
        validateProductDTO(productRequestDTO);
        Product product = productMapper.toEntity(productRequestDTO);
        setCategoryById(productRequestDTO.categoryId(), product);
        ProductResponseDTO createdProduct = customProductMapper.mapToResponseDTO(productRepository.save(product));
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> productList = productRepository.findAll().stream()
                .map(customProductMapper::mapToResponseDTO)
                .toList();
        if (productList.isEmpty()) {
            throw new ListIsEmptyException("No products was found");
        }
        return ResponseEntity.ok(productList);
    }

    public ResponseEntity<ProductResponseDTO> getProductById(Long id) {
        Product product = findProduct(id);
        return ResponseEntity.ok(customProductMapper.mapToResponseDTO(product));
    }

    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        validateProductDTO(productRequestDTO);
        Product existingProduct = findProduct(id);
        customProductMapper.updateEntityFromDTO(existingProduct, productRequestDTO);
        setCategoryById(productRequestDTO.categoryId(), existingProduct);
        ProductResponseDTO updatedProduct = customProductMapper.mapToResponseDTO(productRepository.save(existingProduct));
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        Product existingProduct = findProduct(id);
        productRepository.delete(existingProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validateProductDTO(ProductRequestDTO productRequestDTO) {
        validateNotNull(productRequestDTO, "ProductRequestDTO cannot be null");
        validateNotNull(productRequestDTO.name(), "Product name cannot be null");
        validateNotNull(productRequestDTO.description(), "Product description cannot be null");
        validateNotNull(productRequestDTO.brand(), "Product brand cannot be null");
        validateNotNull(productRequestDTO.material(), "Product material cannot be null");
        validateNotNull(productRequestDTO.categoryId(), "Category ID cannot be null");
        Category category = validateCategoryExistsAndIsActive(productRequestDTO.categoryId());
        validateCategoryIsLeaf(category);
    }

    private void validateNotNull(Object value, String errorMessage) {
        if (value == null) {
            throw new InvalidProductDataException(errorMessage);
        }
    }

    private Category validateCategoryExistsAndIsActive(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new InvalidProductDataException("Category not found");
        }
        Category category = categoryOptional.get();
        if (!category.getActive()) {
            throw new InvalidProductDataException("Category must be active");
        }
        return category;
    }

    private void validateCategoryIsLeaf(Category category) {
        if (!category.getChildren().isEmpty()) {
            throw new InvalidProductDataException("The product must be created on a childless category");
        }
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private void setCategoryById(Long id, Product product) {
        product.setCategory(categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found")));
    }
}