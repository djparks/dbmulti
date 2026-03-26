package net.parksy.dbmulti.service;

import lombok.RequiredArgsConstructor;
import net.parksy.dbmulti.entity.Customer;
import net.parksy.dbmulti.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, String phoneNumber) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
