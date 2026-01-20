package com.Melexworld.controller;


import com.Melexworld.payload.dto.CategoryDTO;
import com.Melexworld.payload.response.ApiResponse;
import com.Melexworld.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryDTO>createCategory(@RequestBody CategoryDTO categoryDto) throws Exception {

        return  ResponseEntity.ok(
                categoryService.createCategory(categoryDto)
        );

    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStoreId(@PathVariable Long storeId)throws Exception{
        return ResponseEntity.ok(
                categoryService.getCategoriesByStore(storeId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDto, @PathVariable Long id) throws Exception{

        return ResponseEntity.ok(
                categoryService.updateCategory(id,categoryDto)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) throws Exception{

        categoryService.updateCategory(id,categoryDTO);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("Category deleted Successfully");
        return ResponseEntity.ok(apiResponse);

    }


}
