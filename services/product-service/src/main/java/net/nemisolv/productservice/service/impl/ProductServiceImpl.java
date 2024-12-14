package net.nemisolv.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.nemisolv.lib.core._enum.PermissionName;
import net.nemisolv.lib.core.exception.ProductPurchaseException;
import net.nemisolv.lib.core.exception.ResourceNotFoundException;
import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.lib.util.ResultCode;
import net.nemisolv.productservice.client.InventoryClient;
import net.nemisolv.productservice.entity.Product;
import net.nemisolv.productservice.mapper.ProductMapper;
import net.nemisolv.productservice.payload.exchange.CheckProductAvailabilityRequest;
import net.nemisolv.productservice.payload.product.request.CreateProductRequest;
import net.nemisolv.productservice.payload.product.request.ProductPurchaseRequest;
import net.nemisolv.productservice.payload.product.request.UpdateProductRequest;
import net.nemisolv.productservice.payload.product.response.ProductOverviewResponse;
import net.nemisolv.productservice.payload.product.response.ProductPurchaseResponse;
import net.nemisolv.productservice.payload.product.response.ProductResponse;
import net.nemisolv.productservice.repository.ProductRepository;
import net.nemisolv.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryClient inventoryClient;

    @Override
    public PagedResponse<ProductOverviewResponse> getProducts(QueryOption queryOption) {
        int page = queryOption.page();
        int limit = queryOption.limit();
        String sortBy = queryOption.sortBy();
        String sortDirection = queryOption.sortDirection();
        String search = queryOption.searchQuery();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Product> productsPage = null;
        if(StringUtils.hasLength(search) ) {
             productsPage = productRepository.searchProducts(search, pageable);
        }else {
             productsPage = productRepository.findAll(pageable);
        }

        return mapToPagedResponse(productsPage);
    }

    private PagedResponse<ProductOverviewResponse> mapToPagedResponse(Page<Product> productsPage) {
        return  PagedResponse.<ProductOverviewResponse>builder()
                .records(
                        productsPage.getContent().stream()
                                .map(productMapper::toOverviewResponse)
                                .toList()
                )
                .pageNo(productsPage.getNumber())
                .pageSize(productsPage.getSize())
                .totalElements(productsPage.getTotalElements())
                .totalPages(productsPage.getTotalPages())
                .last(productsPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        boolean isAccessible = true;

        // temp -> need to call identity service to check
//        boolean isAccessible = AccessHelper.isAccessAllowed(PermissionName.VIEW_SENSITIVE_PRODUCT);
        Product product;
        if(!isAccessible) {
            // means the product is retrieved for the user to view
             product = productRepository.findByIdAndActiveTrue(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Product not found"));
            return productMapper.toResponse(product);
        }
        // means the product is retrieved for the admin to edit
         product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Product not found"));
        return productMapper.toResponse(product);

    }

    @Override
    public PagedResponse<ProductOverviewResponse> getProductsByCategory(Long categoryId, QueryOption queryOption) {
        return null;
    }

    @Override
    public PagedResponse<ProductOverviewResponse> getProductsByBrand(Long brandId, QueryOption queryOption) {
        return null;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest productRequest) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // Validate products

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException(ResultCode.PRODUCT_PURCHASE_ERROR,"One or more products does not exist");
        }

        // check stock in Inventory
        var stockMap = inventoryClient.checkStock(productIds);
        for(var req: request) {
            if(stockMap.get(req.productId()) < req.quantity()) {
                throw new ProductPurchaseException(ResultCode.PRODUCT_PURCHASE_ERROR,"Insufficient stock quantity for product with ID:: " + req.productId());
            }
        }

        // bulk update local stock persist
        request.forEach(req -> {
            var product = storedProducts.stream()
                    .filter(p -> p.getId().equals(req.productId()))
                    .findFirst()
                    .orElseThrow(() -> new ProductPurchaseException(ResultCode.PRODUCT_PURCHASE_ERROR,"Product not found"));
            product.setQuantity(product.getQuantity() - req.quantity());
        });

        productRepository.saveAll(storedProducts);


        // notify inventory service to update stock
        var updateMap = request.stream()
                .collect(Collectors.toMap(ProductPurchaseRequest::productId,
                        ProductPurchaseRequest::quantity));

        inventoryClient.updateStock(updateMap);

        // map to response
        return storedProducts.stream()
                .map(product -> {
                    var req = request.stream()
                            .filter(r -> r.productId().equals(product.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ProductPurchaseException(ResultCode.PRODUCT_PURCHASE_ERROR,"Product not found"));
                    return productMapper.toproductPurchaseResponse(product, req.quantity());
                })
                .toList();
    }
}
