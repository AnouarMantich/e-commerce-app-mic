package org.example.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.example.ecommerce.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public String createCustomer(CustomerRequest request) {
        return repository.save(mapper.toCustomer(request)).getId();
    }


    public void updateCustomer( CustomerRequest request) {
        Customer customer = repository.findById(request.id()).orElseThrow(
                () -> new CustomerNotFoundException(String.format("Could not update customer :: no customer found with provided id :: %s", request.id()))
        );
        mergeCustomer(customer,request);
        repository.save(customer);

    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }


    public List<CustomerResponse> findAllCustomers() {
      return   repository.findAll().stream().map(mapper::toCustomerResponse).toList();
    }

    public Boolean existsById(String customerId) {
       return repository.findById(customerId).isPresent();
    }


    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::toCustomerResponse).orElseThrow(
                () -> new CustomerNotFoundException(String.format(" No customer found with provided id :: %s", customerId))
        );
    }

    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }
}
