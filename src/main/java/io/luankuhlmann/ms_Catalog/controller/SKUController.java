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
        SKUResponseDTO createdSKU = skuService.createSKU(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSKU);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SKUResponseDTO> updateSKU(@PathVariable Long id, @RequestBody @Valid SKURequestDTO data) {
        SKUResponseDTO updatedSKU = skuService.updateSKU(id, data);
        return ResponseEntity.ok(updatedSKU);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSKU(@PathVariable Long id) {
        skuService.deleteSKU(id);
        return ResponseEntity.noContent().build();
    }
}
