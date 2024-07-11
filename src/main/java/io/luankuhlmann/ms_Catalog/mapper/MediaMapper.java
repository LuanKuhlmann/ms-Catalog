package io.luankuhlmann.ms_Catalog.mapper;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.model.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaResponseDTO toResponseDTO(Media media);
    Media toEntity(MediaRequestDTO mediaRequestDTO);
}
