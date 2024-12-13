package net.nemisolv.productservice.mapper;

import net.nemisolv.productservice.entity.Product;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toResponse(Product product);

    ProductOverviewResponse toOverviewResponse(Product product);

    Product toEntity(ProductResponse productResponse);


}
