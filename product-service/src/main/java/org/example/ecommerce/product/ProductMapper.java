package org.example.ecommerce.product;

import org.example.ecommerce.category.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        product.setCategory(Category.builder()
                        .id(productRequest.categoryId())
                        .build());
        return product;
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setDescription(product.getCategory().getDescription());
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    public ProductPurchaseResponse toProductPurchasedResponse
            (Product product,  double quantity) {
        ProductPurchaseResponse productPurchaseResponse = new ProductPurchaseResponse();
        productPurchaseResponse.setProductId(product.getId());
        productPurchaseResponse.setQuantity(quantity);
        productPurchaseResponse.setName(product.getName());
        productPurchaseResponse.setDescription(product.getDescription());
        productPurchaseResponse.setPrice(product.getPrice());
        return productPurchaseResponse;
    }
}
