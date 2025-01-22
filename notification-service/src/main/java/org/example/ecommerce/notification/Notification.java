package org.example.ecommerce.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerce.kafka.order.OrderConfirmation;
import org.example.ecommerce.kafka.payment.PaymentConfirmation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationTime;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
