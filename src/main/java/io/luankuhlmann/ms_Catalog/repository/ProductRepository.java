package io.luankuhlmann.ms_Catalog.repository;

import io.luankuhlmann.ms_Catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
