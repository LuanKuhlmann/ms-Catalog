package io.luankuhlmann.ms_Catalog.repository;

import io.luankuhlmann.ms_Catalog.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
