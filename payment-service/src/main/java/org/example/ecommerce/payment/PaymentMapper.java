package org.example.ecommerce.payment;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentRequest, payment);
        return payment;
    }




}
