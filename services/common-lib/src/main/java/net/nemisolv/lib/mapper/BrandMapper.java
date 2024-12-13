package net.nemisolv.lib.mapper;

import net.nemisolv.techshop.entity.Brand;
import net.nemisolv.techshop.payload.brand.BrandRequest;
import net.nemisolv.techshop.payload.brand.BrandResponse;
import org.springframework.stereotype.Service;

@Service
public class BrandMapper {
    public Brand toEntity(BrandRequest request) {
        return Brand.builder()
                .name(request.name())
                .description(request.description())
                .logoUrl(request.logoUrl())
                .build();
    }

    public BrandResponse toResponse(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getLogoUrl()
        );
    }
}
