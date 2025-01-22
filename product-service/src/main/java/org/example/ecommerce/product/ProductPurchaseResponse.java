package org.example.ecommerce.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseResponse {
  private   Integer productId;
  private   String name;
  private   String description;
  private   BigDecimal price;
  private   double quantity;
}
