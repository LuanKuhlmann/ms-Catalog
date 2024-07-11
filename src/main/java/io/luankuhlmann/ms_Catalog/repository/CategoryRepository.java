package io.luankuhlmann.ms_Catalog.repository;

import io.luankuhlmann.ms_Catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
