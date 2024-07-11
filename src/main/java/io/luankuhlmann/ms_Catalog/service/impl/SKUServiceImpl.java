package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.mapper.CustomSKUMapper;
import io.luankuhlmann.ms_Catalog.mapper.SKUMapper;
import io.luankuhlmann.ms_Catalog.model.SKU;
import io.luankuhlmann.ms_Catalog.repository.ProductRepository;
import io.luankuhlmann.ms_Catalog.repository.SKURepository;
import io.luankuhlmann.ms_Catalog.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public SKUResponseDTO createSKU(SKURequestDTO skuRequestDTO) {
        validateSKUDTO(skuRequestDTO);

        SKU sku = skuMapper.toEntity(skuRequestDTO);
        sku.setProduct(productRepository.findById(skuRequestDTO.productId()).orElseThrow());

        SKU savedSKU = skuRepository.save(sku);
        return customSKUMapper.mapToResponseDTO(savedSKU);
    }

    public SKUResponseDTO updateSKU(Long id, SKURequestDTO skuRequestDTO) {
        validateSKUDTO(skuRequestDTO);

        SKU existingSKU = skuRepository.findById(id).orElseThrow();
        existingSKU.setPrice(skuRequestDTO.price());
        existingSKU.setQuantity(skuRequestDTO.quantity());
        existingSKU.setColor(skuRequestDTO.color());
        existingSKU.setSize(skuRequestDTO.size());
        existingSKU.setHeight(skuRequestDTO.height());
        existingSKU.setWidth(skuRequestDTO.width());
        existingSKU.setProduct(productRepository.findById(skuRequestDTO.productId()).orElseThrow());

        SKU updatedSKU = skuRepository.save(existingSKU);
        return skuMapper.toResponseDTO(updatedSKU);
    }

    public void deleteSKU(Long id) {
        skuRepository.deleteById(id);
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
}