package net.nemisolv.lib.mapper;

import net.nemisolv.techshop.entity.Product;
import net.nemisolv.techshop.payload.product.request.CreateProductRequest;
import net.nemisolv.techshop.payload.product.response.ProductOverviewResponse;
import net.nemisolv.techshop.payload.product.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toEntity(CreateProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .mainImgUrl(request.mainImgUrl())
                .price(request.price())
                .unit(request.unit())
                .quantity(request.quantity())
                .videoUrl(request.videoUrl())
                .active(request.active())
                .reviewStory(request.reviewStory())
                .build();
    }

    public ProductOverviewResponse toOverviewResponse(Product product) {
        return new ProductOverviewResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getMainImgUrl(),
                product.getPrice(),
                product.getBrand() == null ? null :   product.getBrand().getId(),
                product.getCategory() == null ? null : product.getCategory().getId()
        );
    }
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getMainImgUrl(),
                product.getPrice(),
                product.getSku(),
                product.getUnit(),
                product.getQuantity(),
                product.getAverageRating(),
                product.getVideoUrl(),
                product.isActive(),
                product.getReviewStory(),
                product.getBrand() == null ? null : product.getBrand().getId(),
                product.getCategory() == null ? null : product.getCategory().getId()
        );
    }
}
