package net.nemisolv.productservice.mapper;

import net.nemisolv.productservice.entity.Category;
import net.nemisolv.productservice.entity.Product;
import net.nemisolv.productservice.payload.category.CategoryRequest;
import net.nemisolv.productservice.payload.category.CategoryResponse;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);


    Category toEntity(CategoryRequest request);


}
