package io.luankuhlmann.ms_Catalog.dto.request;

import io.luankuhlmann.ms_Catalog.model.Media;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record SKURequestDTO(
        @NotBlank BigDecimal price,
        @NotBlank Integer quantity,
        @NotBlank String color,
        @NotBlank String size,
        @NotBlank Integer height,
        @NotBlank Integer width,
        @NotBlank Long productId
) {
}
