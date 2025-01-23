package net.nemisolv.productservice.payload.product.request;


public record CreateProductVariantRequest(
      String name,
      String value,
      Long productId
) {

}
