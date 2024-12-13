package net.nemisolv.lib.mapper;

import net.nemisolv.techshop.entity.Category;
import net.nemisolv.techshop.payload.category.CategoryRequest;
import net.nemisolv.techshop.payload.category.CategoryResponse;
import org.springframework.stereotype.Service;

@Service

public class CategoryMapper {
    public Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }
}
