package net.nemisolv.productservice.mapper;

import net.nemisolv.productservice.entity.Product;
import net.nemisolv.productservice.payload.product.request.CreateProductRequest;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductPurchaseResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "brand.id", target = "brandId")
    ProductResponse toResponse(Product product);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "brand.id", target = "brandId")
    ProductOverviewResponse toOverviewResponse(Product product);

//    @Mapping(source = "category.id", target = "categoryId")
//    @Mapping(source = "brand.id", target = "brandId")
    ProductPurchaseResponse toproductPurchaseResponse(Product product, int quantity);

    Product toProduct(CreateProductRequest request);


}
