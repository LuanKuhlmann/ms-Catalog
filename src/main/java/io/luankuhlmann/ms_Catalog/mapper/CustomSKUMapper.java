package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomSKUMapper {
    @Autowired
    private CustomMediaMapper customMediaMapper;

    public SKUResponseDTO mapToResponseDTO(SKU sku) {
        List<MediaResponseDTO> images = new ArrayList<>();
        if (sku.getImages() != null) {
            images = sku.getImages().stream()
                    .map(customMediaMapper::mapToResponseDTO)
                    .collect(Collectors.toList());
        }


        return new SKUResponseDTO(
                sku.getId(),
                sku.getPrice(),
                sku.getQuantity(),
                sku.getColor(),
                sku.getSize(),
                sku.getHeight(),
                sku.getWidth(),
                images
        );
    }
}
