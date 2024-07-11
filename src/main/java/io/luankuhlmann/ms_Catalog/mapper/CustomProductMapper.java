package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.request.ProductRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.ProductResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.SKUResponseDTO;
import io.luankuhlmann.ms_Catalog.model.Product;
import io.luankuhlmann.ms_Catalog.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomProductMapper {
    @Autowired
    private CustomSKUMapper skuMapper;

    public ProductResponseDTO mapToResponseDTO(Product product) {
        List<SKUResponseDTO> skus = new ArrayList<>();
        if (product.getSkus() != null) {
            skus = product.getSkus().stream()
                    .map(skuMapper::mapToResponseDTO)
                    .collect(Collectors.toList());
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getMaterial(),
                product.getActive(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                skus
        );
    }

    public void updateEntityFromDTO(Product product, ProductRequestDTO productRequestDTO) {
        product.setName(productRequestDTO.name());
        product.setBrand(productRequestDTO.brand());
        product.setDescription(productRequestDTO.description());
        product.setMaterial(productRequestDTO.material());
        product.setActive(productRequestDTO.active());
    }
}
