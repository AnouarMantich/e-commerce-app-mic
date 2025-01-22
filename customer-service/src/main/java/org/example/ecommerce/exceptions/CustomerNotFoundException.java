package org.example.ecommerce.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String format) {
        super(format);
    }
}
