package org.example.ecommerce.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.example.ecommerce.kafka.order.Product;
import org.example.ecommerce.kafka.payment.PaymentConfirmation;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference

    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        messageHelper.setFrom("mantich00anouar@gmail.com");

        String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());


        try {

            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        }
        catch (Exception e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }

    public void sendOrderSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products

    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        messageHelper.setFrom("mantich00anouar@gmail.com");

        String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("totalAmount", amount);
        model.put("orderReference", orderReference);
        model.put("products", products);

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());


        try {

            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        }
        catch (Exception e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }

}
