package com.example.customerservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setupDb() {
        customerRepository.deleteAll();
    }

    @Test
    public void whenCustomerExists_ReturnCustomerByCustomerId() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customer1.getCustomerIdentifier().getCustomerId());

        //assert
        assertNotNull(customer);
        assertEquals(customer.getCustomerIdentifier(), customer1.getCustomerIdentifier());
        //expected vs actual
        assertEquals(customer.getFirstName(), customer1.getFirstName());
        assertEquals(customer.getLastName(), customer1.getLastName());
    }

    @Test
    public void whenCustomerDoesNotExist_ReturnNull() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId("05c8ab76-4f75-45c1-b6e2-aa8e91488888");

        //assert
        assertNull(customer);

    }

    @Test
    public void whenCustomerIdIsInvalid_ReturnNull() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId("05c8ab76-4f75-45c1-b6e2");

        //assert
        assertNull(customer);
    }

    @Test
    public void whenMemberIdIsNull_ReturnNull() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(null);

        //assert
        assertNull(customer);
    }

    @Test
    public void whenCustomerIdIsEmpty_ReturnNull() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId("");

        //assert
        assertNull(customer);
    }


    @Test
    public void whenCustomerIdIsBlank_ReturnNull() {
        //arrange
        Customer customer1 = new Customer("John", "Doe", "john@gmail.com",
                new Address("12 Main St", "New York", "NY", "UnitedStates", "10001"),
                new ArrayList<>());

        customerRepository.save(customer1);

        //act
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(" ");

        //assert
        assertNull(customer);
    }

}


