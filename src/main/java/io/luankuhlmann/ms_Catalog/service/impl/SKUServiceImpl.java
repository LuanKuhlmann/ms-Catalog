package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.exception.EntityNotFoundException;
import io.luankuhlmann.ms_Catalog.mapper.CustomSKUMapper;
import io.luankuhlmann.ms_Catalog.mapper.SKUMapper;
import io.luankuhlmann.ms_Catalog.model.SKU;
import io.luankuhlmann.ms_Catalog.repository.ProductRepository;
import io.luankuhlmann.ms_Catalog.repository.SKURepository;
import io.luankuhlmann.ms_Catalog.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SKUServiceImpl implements SKUService {
    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SKUMapper skuMapper;

    @Autowired
    private CustomSKUMapper customSKUMapper;

    public ResponseEntity<SKUResponseDTO> createSKU(SKURequestDTO skuRequestDTO) {
        validateSKUDTO(skuRequestDTO);
        SKU sku = skuMapper.toEntity(skuRequestDTO);
        sku.setProduct(productRepository.findById(skuRequestDTO.productId()).orElseThrow(() -> new EntityNotFoundException("Product not Found")));
        SKUResponseDTO createdSKU = customSKUMapper.mapToResponseDTO(skuRepository.save(sku));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSKU);
    }

    public  ResponseEntity<SKUResponseDTO> updateSKU(Long id, SKURequestDTO skuRequestDTO) {
        validateSKUDTO(skuRequestDTO);
        SKU existingSKU = findSkuById(id);
        customSKUMapper.updateEntityFromDTO(existingSKU, skuRequestDTO);
        //existingSKU.setProduct(productRepository.findById(skuRequestDTO.productId()).orElseThrow(() -> new EntityNotFoundException("Product not Found")));
        SKUResponseDTO updatedSKU = skuMapper.toResponseDTO(skuRepository.save(existingSKU));
        return new ResponseEntity<>(updatedSKU, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSKU(Long id) {
        SKU existingSku = findSkuById(id);
        skuRepository.delete(existingSku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void adjustStock(Long skuId, int quantity) {
        SKU sku = skuRepository.findById(skuId).orElseThrow();
        sku.setQuantity(sku.getQuantity() - quantity);
        skuRepository.save(sku);
    }

    private void validateSKUDTO(SKURequestDTO skuRequestDTO) {
        if (skuRequestDTO.price() == null || skuRequestDTO.quantity() < 0 ||
                skuRequestDTO.color() == null || skuRequestDTO.size() == null ||
                skuRequestDTO.height() <= 0 || skuRequestDTO.width() <= 0 ||
                skuRequestDTO.productId() == null ||
                !productRepository.existsById(skuRequestDTO.productId())) {
            throw new IllegalArgumentException("Invalid SKU data");
        }
    }

    private SKU findSkuById(Long id) {
        return skuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sku not Found"));
    }
}