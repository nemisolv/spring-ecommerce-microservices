package net.nemisolv.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.productservice.payload.category.CategoryRequest;
import net.nemisolv.productservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<?> getAllCategories() {
        return ApiResponse.success(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getCategoryById(@PathVariable Long id) {
        return ApiResponse.success(categoryService.getCategory(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_CATEGORY')")
    public ApiResponse<?> addCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ApiResponse.success(categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_CATEGORY')")
    public ApiResponse<?> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return ApiResponse.success(categoryService.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('DELETE_CATEGORY')")
    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("Category deleted successfully");
    }
}
