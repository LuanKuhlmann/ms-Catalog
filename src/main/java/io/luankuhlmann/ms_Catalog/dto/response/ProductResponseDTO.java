package io.luankuhlmann.ms_Catalog.dto.response;

import io.luankuhlmann.ms_Catalog.model.Category;

import java.util.List;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String brand,
        String material,
        Boolean active,
        Long categoryId,
        String categoryName,
        List<SKUResponseDTO> skus
) {
}
