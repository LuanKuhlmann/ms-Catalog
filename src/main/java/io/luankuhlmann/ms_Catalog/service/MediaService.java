package io.luankuhlmann.ms_Catalog.service;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MediaService {
    ResponseEntity<MediaResponseDTO> createMedia(MediaRequestDTO mediaRequestDTO);

    ResponseEntity<Void> deleteMedia(Long id);
}
