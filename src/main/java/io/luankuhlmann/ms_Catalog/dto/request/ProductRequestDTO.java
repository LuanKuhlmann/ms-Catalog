package io.luankuhlmann.ms_Catalog.dto.request;

import io.luankuhlmann.ms_Catalog.model.Category;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String brand,
        @NotBlank String material,
        @NotBlank Boolean active,
        @NotBlank Long categoryId
) {
}
