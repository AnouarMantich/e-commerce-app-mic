package org.example.ecommerce.email;


import lombok.Getter;

@Getter
public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment success processed"),
    ORDER_CONFIRMATION("order-confirmation.html","Order successffully processed");


    private final String template;
    private final String subject;

     EmailTemplates(String template,String subject){
        this.template = template;
        this.subject = subject;
    }

}
