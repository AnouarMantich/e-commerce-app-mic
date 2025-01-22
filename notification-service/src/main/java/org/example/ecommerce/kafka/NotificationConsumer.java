package org.example.ecommerce.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommerce.email.EmailService;
import org.example.ecommerce.kafka.order.OrderConfirmation;
import org.example.ecommerce.kafka.payment.PaymentConfirmation;
import org.example.ecommerce.notification.Notification;
import org.example.ecommerce.notification.NotificationRepository;
import org.example.ecommerce.notification.NotificationType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Payment confirmation: {}", paymentConfirmation);
        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                .build()
        );

//        send email

      String customerName=  paymentConfirmation.customerFirstname()+" "+paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotifications(OrderConfirmation orderConfirmation)
            throws MessagingException
    {
        log.info("Order confirmation: {}", orderConfirmation);
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationTime(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        //        send email
        String customerName=  orderConfirmation.customer().firstName()+" "+orderConfirmation.customer().lastName();
        emailService.sendOrderSuccessEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );


    }

}
