package org.example.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommerce.OrderLine.OrderLineRequest;
import org.example.ecommerce.OrderLine.OrderLineService;
import org.example.ecommerce.payment.PaymentClient;
import org.example.ecommerce.payment.PaymentRequest;
import org.example.ecommerce.customer.CustomerClient;
import org.example.ecommerce.customer.CustomerResponse;
import org.example.ecommerce.exceptions.BusinessException;
import org.example.ecommerce.kafka.OrderConfirmation;
import org.example.ecommerce.kafka.OrderProducer;
import org.example.ecommerce.product.ProductClient;
import org.example.ecommerce.product.PurchaseRequest;
import org.example.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    public Integer createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.findCustomerById(request.customerId()).orElseThrow(
                () -> new BusinessException("Cannot create order:: No customer found whit the provided id !")
        );


        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());
        Order order = repository.save(mapper.toOrder(request));


        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }



        var paymentRequest =new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        log.info("Created order from request: {}", request);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }


    public List<OrderResponse> findAllOrders(){
        return repository.findAll().stream().map(mapper::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Integer id) {
        return repository.findById(id).map(mapper::toOrderResponse).orElseThrow(
                () -> new EntityNotFoundException("Cannot find order by the id :: "+id)
        );
    }
}
