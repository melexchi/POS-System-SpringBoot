package com.Melexworld.repository;

import com.Melexworld.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStoreId(long storeId);
}
