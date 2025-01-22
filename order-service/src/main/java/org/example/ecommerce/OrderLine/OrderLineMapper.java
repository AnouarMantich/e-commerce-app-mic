package org.example.ecommerce.OrderLine;

import org.example.ecommerce.order.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine( OrderLineRequest request) {
        OrderLine orderLine = new OrderLine();
        BeanUtils.copyProperties( request, orderLine );
        orderLine.setOrder(Order.builder()
                        .id(request.orderId())
                .build());
        return orderLine;
    }


    public OrderLineResponse toOrderLineResponse(OrderLine orderLine){
        OrderLineResponse orderLineResponse = new OrderLineResponse();
        BeanUtils.copyProperties( orderLine, orderLineResponse );
        return orderLineResponse;
    }

}
