package io.luankuhlmann.ms_Catalog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank String name,
        @NotBlank Boolean active,
        Long parentId
) {
}
