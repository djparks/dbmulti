package net.parksy.dbmulti.service;

import net.parksy.dbmulti.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testCreateAndGetCustomer() {
        // Given
        String firstName = "Jane";
        String lastName = "Doe";
        String phoneNumber = "555-1234";

        // When
        Customer savedCustomer = customerService.createCustomer(firstName, lastName, phoneNumber);

        // Then
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getFirstName()).isEqualTo(firstName);
        assertThat(savedCustomer.getLastName()).isEqualTo(lastName);

        // And When
        List<Customer> foundCustomers = customerService.getCustomersByLastName(lastName);

        // Then
        assertThat(foundCustomers).isNotEmpty();
        assertThat(foundCustomers.get(0).getFirstName()).isEqualTo(firstName);
    }

    @Test
    void testGetAllCustomers() {
        // Given
        customerService.createCustomer("Alice", "Smith", "111");
        customerService.createCustomer("Bob", "Jones", "222");

        // When
        List<Customer> customers = customerService.getAllCustomers();

        // Then
        assertThat(customers).hasSizeGreaterThanOrEqualTo(2);
        assertThat(customers).extracting(Customer::getFirstName).contains("Alice", "Bob");
    }
}
