package io.luankuhlmann.ms_Catalog.service.impl;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.mapper.CustomMediaMapper;
import io.luankuhlmann.ms_Catalog.mapper.MediaMapper;
import io.luankuhlmann.ms_Catalog.model.Media;
import io.luankuhlmann.ms_Catalog.repository.MediaRepository;
import io.luankuhlmann.ms_Catalog.repository.SKURepository;
import io.luankuhlmann.ms_Catalog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public MediaResponseDTO createMedia(MediaRequestDTO mediaRequestDTO) {
        validateMediaDTO(mediaRequestDTO);

        Media media = mediaMapper.toEntity(mediaRequestDTO);

        media.setSku(skuRepository.findById(mediaRequestDTO.skuId()).orElseThrow());

        Media savedMedia = mediaRepository.save(media);
        return customMediaMapper.mapToResponseDTO(savedMedia);
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }

    private void validateMediaDTO(MediaRequestDTO mediaRequestDTO) {
        if (mediaRequestDTO.imageUrl() == null || mediaRequestDTO.skuId() == null ||
                !skuRepository.existsById(mediaRequestDTO.skuId())) {
            throw new IllegalArgumentException("Invalid media data");
        }
    }
}
