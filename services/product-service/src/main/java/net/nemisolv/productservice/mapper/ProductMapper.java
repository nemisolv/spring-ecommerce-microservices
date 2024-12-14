package net.nemisolv.productservice.mapper;

import net.nemisolv.productservice.entity.Product;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductPurchaseResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toResponse(Product product);

    ProductOverviewResponse toOverviewResponse(Product product);

    ProductPurchaseResponse toproductPurchaseResponse(Product product, int quantity);

    Product toEntity(ProductResponse productResponse);


}
