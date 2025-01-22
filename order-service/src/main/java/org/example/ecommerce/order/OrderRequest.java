package org.example.ecommerce.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.ecommerce.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "The amount should be positive !")
        BigDecimal amount,
        @NotNull(message = "The payment method should be precised !")
        PaymentMethod paymentMethod,
        @NotNull(message = "The customer should be present !")
        @NotEmpty(message = "The customer should be present !")
        @NotBlank(message = "The customer should be present !")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product !")
        List<PurchaseRequest> products
) {
}
