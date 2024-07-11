package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.model.SKU;
import org.springframework.stereotype.Service;

@Service
public interface SKUService {
    SKUResponseDTO createSKU(SKURequestDTO skuRequestDTO);

    SKUResponseDTO updateSKU(Long id, SKURequestDTO skuRequestDTO);

    void deleteSKU(Long id);

    void adjustStock(Long skuId, int quantity);
}
