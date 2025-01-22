package org.example.ecommerce.customer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Validated
public class Address {

    private String street;
    private String houseNumber;
    private String zipCode;

}
