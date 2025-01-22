package org.example.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.exceptions.ProductPurchaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);
        repository.save(product);
        return product.getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        List<Integer> productIds = request.stream().map(ProductPurchaseRequest::productId).toList();

        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var sortedRequests =
                request
                        .stream()
                        .sorted(
                                Comparator.comparing(ProductPurchaseRequest::productId)
                        ).toList();
        ArrayList<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = sortedRequests.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()){
                throw  new ProductPurchaseException
                        ("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchasedResponse(product,productRequest.quantity()));

        }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(
                ()-> new EntityNotFoundException(
                        String.format("No product with the id %d was found !",id))
        );
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(mapper::toProductResponse).collect(Collectors.toList());
    }
}
