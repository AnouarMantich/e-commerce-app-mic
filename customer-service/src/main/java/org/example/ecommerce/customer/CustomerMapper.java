package org.example.ecommerce.customer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        return customer;
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
       return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

}
