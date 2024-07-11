package io.luankuhlmann.ms_Catalog.controller;

import io.luankuhlmann.ms_Catalog.dto.request.MediaRequestDTO;
import io.luankuhlmann.ms_Catalog.dto.response.MediaResponseDTO;
import io.luankuhlmann.ms_Catalog.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping
    public ResponseEntity<MediaResponseDTO> createMedia(@RequestBody @Valid MediaRequestDTO data) {
        MediaResponseDTO createdMedia = mediaService.createMedia(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}