package io.luankuhlmann.ms_Catalog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MediaRequestDTO(
        @NotBlank String imageUrl,
        @NotBlank Long skuId
) {
}
