package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MediaService {
    MediaResponseDTO createMedia(MediaRequestDTO mediaRequestDTO);

    void deleteMedia(Long id);
}
