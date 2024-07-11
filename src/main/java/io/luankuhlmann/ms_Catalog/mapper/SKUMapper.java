package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.request.SKURequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.model.SKU;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SKUMapper {
    SKUResponseDTO toResponseDTO(SKU sku);

    SKU toEntity(SKURequestDTO skuRequestDTO);
}
