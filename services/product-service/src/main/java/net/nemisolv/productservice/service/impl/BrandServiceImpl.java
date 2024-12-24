package net.nemisolv.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.nemisolv.productservice.entity.Brand;
import net.nemisolv.productservice.mapper.BrandMapper;
import net.nemisolv.productservice.payload.brand.BrandRequest;
import net.nemisolv.productservice.payload.brand.BrandResponse;
import net.nemisolv.productservice.repository.BrandRepository;
import net.nemisolv.productservice.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;


    @Override
    public BrandResponse createBrand(BrandRequest request) {
        Brand brand = brandMapper.toEntity(request);
        brandRepository.findByNameIgnoreCase(brand.getName())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Brand already exists");
                });
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brand.setName(request.name());
            brand.setLogoUrl(request.logoUrl());
            if (request.description() != null) {
                brand.setDescription(request.description());
            }
            return brandMapper.toResponse(brandRepository.save(brand));
        } else {
            throw new IllegalArgumentException("Brand not found");
        }
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public BrandResponse getBrand(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
    }

    @Override
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .toList();
    }
}
