package org.example.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "Product name is required !")
         String name,
         @NotNull(message = "Product name is required !")
         String description,
         @Positive(message = "The quantity should be positive")
         double availableQuantity,
         @Positive(message = "The Price should be positive")
         BigDecimal price,
         @NotNull(message = "The category is required !")
         Integer categoryId
) {
}
