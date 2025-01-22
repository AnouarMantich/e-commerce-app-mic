package org.example.ecommerce.OrderLine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class OrderLineResponse {
    Integer id;
    double quantity;
}
