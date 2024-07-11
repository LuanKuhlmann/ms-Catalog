package io.luankuhlmann.ms_Catalog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record CategoryResponseDTO (
        Long id,
        String name,
        Boolean active,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<CategoryResponseDTO> children
) {
}
