package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.ProductRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO);

    ResponseEntity<List<ProductResponseDTO>> getAllProducts();

    ResponseEntity<ProductResponseDTO> getProductById(Long id);

    ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO productRequestDTO);

    ResponseEntity<Void> deleteProduct(Long id);
}
