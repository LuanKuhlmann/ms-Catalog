package io.luankuhlmann.ms_Catalog.controller;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.service.SKUService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/skus")
public class SKUController {
    @Autowired
    private SKUService skuService;

    @PostMapping
    public ResponseEntity<SKUResponseDTO> createSKU(@RequestBody @Valid SKURequestDTO data) {
        return skuService.createSKU(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SKUResponseDTO> updateSKU(@PathVariable Long id, @RequestBody @Valid SKURequestDTO data) {
        return skuService.updateSKU(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSKU(@PathVariable Long id) {
        return skuService.deleteSKU(id);
    }
}
