package org.example.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "The product is mandatory")
        Integer productId,
        @NotNull(message = "The quantity is mandatory")
        double quantity
) {
}
