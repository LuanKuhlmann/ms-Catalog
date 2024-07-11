package io.luankuhlmann.ms_Catalog.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SKUResponseDTO(
        Long id,
        BigDecimal price,
        Integer quantity,
        String color,
        String size,
        Integer height,
        Integer width,
        List<MediaResponseDTO> images
) {
}
