package net.nemisolv.productservice.service;


import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.productservice.payload.product.request.CreateProductRequest;
import net.nemisolv.productservice.payload.product.request.ProductPurchaseRequest;
import net.nemisolv.productservice.payload.product.request.UpdateProductRequest;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductPurchaseResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;

import java.util.List;

public interface ProductService {
    PagedResponse<ProductOverviewResponse> getProducts(QueryOption queryOption);

    ProductResponse getProductById(Long  id);

    PagedResponse<ProductOverviewResponse> getProductsByCategory(Long categoryId, QueryOption queryOption);

    PagedResponse<ProductOverviewResponse> getProductsByBrand(Long brandId, QueryOption queryOption);

    ProductResponse createProduct(CreateProductRequest productRequest);

    ProductResponse updateProduct(Long id, UpdateProductRequest productRequest);

    void deleteProduct(Long id);

    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);

//    void updateStock(Long productId, int quantityDelta);
}
