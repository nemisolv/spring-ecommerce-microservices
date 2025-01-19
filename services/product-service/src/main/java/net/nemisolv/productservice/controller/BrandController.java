package net.nemisolv.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.productservice.payload.brand.BrandRequest;
import net.nemisolv.productservice.payload.brand.BrandResponse;
import net.nemisolv.productservice.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ApiResponse<?> getBrands() {
        return ApiResponse.success(brandService.getBrands());
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getBrand(@PathVariable Long id) {
        return ApiResponse.success(brandService.getBrand(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_BRAND')")
    public ApiResponse<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        return ApiResponse.success(brandService.createBrand(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_BRAND')")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long id,
                                                  @Valid @RequestBody BrandRequest request) {
        return ApiResponse.success(brandService.updateBrand(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_BRAND')")
    public ApiResponse<?> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.success(HttpStatus.OK);
    }
}
