package io.luankuhlmann.ms_Catalog.repository;

import io.luankuhlmann.ms_Catalog.model.SKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKURepository extends JpaRepository<SKU, Long> {
}
