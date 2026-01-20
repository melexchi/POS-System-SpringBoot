package com.Melexworld.service;

import com.Melexworld.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO dto) throws Exception;
    List<CategoryDTO> getCategoriesByStore(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO dto) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
