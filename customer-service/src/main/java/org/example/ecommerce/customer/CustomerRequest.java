package org.example.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "The first name is required !")
        String firstName,
        @NotNull(message = "The last name is required !")
        String lastName,
        @NotNull(message = "The email is required !")
        @Email(message = "This email address is not valid !")
        String email,
        Address address
) {
}
