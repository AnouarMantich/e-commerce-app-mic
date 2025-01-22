package org.example.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
           @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(this.service.createOrder(request));

    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        return ResponseEntity.ok(service.findAllOrders());
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<OrderResponse> findOrderById(
            @PathVariable(name = "order_id") Integer id
    ){
        return ResponseEntity.ok(service.findOrderById(id));
    }



}
