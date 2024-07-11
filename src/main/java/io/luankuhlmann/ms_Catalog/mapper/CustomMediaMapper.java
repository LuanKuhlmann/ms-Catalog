package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.model.Media;
import org.springframework.stereotype.Component;

@Component
public class CustomMediaMapper {
    public MediaResponseDTO mapToResponseDTO(Media media) {
        return new MediaResponseDTO(
                media.getId(),
                media.getImageUrl()
        );
    }
}
