package io.luankuhlmann.ms_Catalog.repository;

import io.luankuhlmann.ms_Catalog.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}
