package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.CategoryResponseDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.exception.EntityNotFoundException;
import io.luankuhlmann.ms_Catalog.exception.InvalidProductDataException;
import io.luankuhlmann.ms_Catalog.mapper.CustomMediaMapper;
import io.luankuhlmann.ms_Catalog.mapper.MediaMapper;
import io.luankuhlmann.ms_Catalog.model.Media;
import io.luankuhlmann.ms_Catalog.repository.MediaRepository;
import io.luankuhlmann.ms_Catalog.repository.SKURepository;
import io.luankuhlmann.ms_Catalog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private CustomMediaMapper customMediaMapper;

    public ResponseEntity<MediaResponseDTO> createMedia(MediaRequestDTO mediaRequestDTO) {
        validateMediaDTO(mediaRequestDTO);
        Media media = mediaMapper.toEntity(mediaRequestDTO);
        media.setSku(skuRepository.findById(mediaRequestDTO.skuId()).orElseThrow(() -> new EntityNotFoundException("SKU not found!")));
        MediaResponseDTO cratedMedia = customMediaMapper.mapToResponseDTO(mediaRepository.save(media));
        return new ResponseEntity<>(cratedMedia, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteMedia(Long id) {
        Media media = mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Media not found"));
        mediaRepository.delete(media);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateMediaDTO(MediaRequestDTO mediaRequestDTO) {
        if (mediaRequestDTO.imageUrl() == null || mediaRequestDTO.skuId() == null ||
                !skuRepository.existsById(mediaRequestDTO.skuId())) {
            throw new InvalidProductDataException("Invalid media data");
        }
    }
}
