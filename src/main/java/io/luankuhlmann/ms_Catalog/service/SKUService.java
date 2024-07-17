package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SKUService {
    ResponseEntity<SKUResponseDTO> createSKU(SKURequestDTO skuRequestDTO);

    ResponseEntity<SKUResponseDTO> updateSKU(Long id, SKURequestDTO skuRequestDTO);

    ResponseEntity<Void> deleteSKU(Long id);
}
