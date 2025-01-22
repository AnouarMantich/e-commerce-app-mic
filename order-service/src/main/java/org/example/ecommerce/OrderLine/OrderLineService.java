package org.example.ecommerce.OrderLine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;


    public Integer saveOrderLine(OrderLineRequest request){
        OrderLine orderLine = mapper.toOrderLine(request);
        return repository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId){
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .toList();
    }




}
