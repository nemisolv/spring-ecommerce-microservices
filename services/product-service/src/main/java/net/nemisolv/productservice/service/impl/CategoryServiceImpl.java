package net.nemisolv.productservice.service.impl;

import lombok.RequiredArgsConstructor;

import net.nemisolv.lib.util.ResultCode;
import net.nemisolv.productservice.entity.Category;
import net.nemisolv.productservice.exception.BadRequestException;
import net.nemisolv.productservice.mapper.CategoryMapper;
import net.nemisolv.productservice.payload.category.CategoryRequest;
import net.nemisolv.productservice.payload.category.CategoryResponse;
import net.nemisolv.productservice.repository.CategoryRepository;
import net.nemisolv.productservice.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        // check if category already exists
        categoryRepository.findByNameIgnoreCase(category.getName())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Category already exists");
                });
        if(request.parentId() != null) {
            Category parentCategory = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            category.setParent(parentCategory);
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            // check name
            categoryRepository.findByNameIgnoreCase(request.name())
                    .ifPresent(c -> {
                        if(!c.getId().equals(id)) {
                            throw new BadRequestException(ResultCode.CATEGORY_NAME_EXISTS);
                        }
                    });


            category.setName(request.name());
            if(StringUtils.hasLength(request.description())) {
                category.setDescription(request.description());
            }
            return categoryMapper.toResponse(categoryRepository.save(category));
        } else {
            throw new BadRequestException(ResultCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()) {
            throw new BadRequestException(ResultCode.CATEGORY_NOT_FOUND);
        }
        Category category = categoryOptional.get();
        if(category.getProducts().isEmpty()) {
            categoryRepository.delete(category);
        } else {
            throw new IllegalArgumentException("Category has some products associated with it");
        }

    }

    @Override
    public CategoryResponse getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
