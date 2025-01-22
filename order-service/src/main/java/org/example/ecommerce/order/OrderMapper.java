package org.example.ecommerce.order;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request){
        if(request == null) return null;
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        BeanUtils.copyProperties(request, order);
        return order;
    }

    public OrderResponse toOrderResponse(Order order){
        if(order == null) return null;
        OrderResponse response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }

}
