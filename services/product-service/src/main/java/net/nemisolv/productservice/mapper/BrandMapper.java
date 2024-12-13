package net.nemisolv.productservice.mapper;

import net.nemisolv.productservice.entity.Brand;
import net.nemisolv.productservice.payload.brand.BrandRequest;
import net.nemisolv.productservice.payload.brand.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandRequest request);

    BrandResponse toResponse(Brand brand);
}
