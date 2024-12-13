package net.nemisolv.productservice.service;


import net.nemisolv.productservice.payload.category.CategoryRequest;
import net.nemisolv.productservice.payload.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
    CategoryResponse getCategory(Long id);
    List<CategoryResponse> getCategories();


}
