package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.request.ProductRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO productDTO);
}