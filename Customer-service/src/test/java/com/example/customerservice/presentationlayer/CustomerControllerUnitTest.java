package com.example.customerservice.presentationlayer;

import com.example.customerservice.busnesslayer.CustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = CustomerController.class)
public class CustomerControllerUnitTest {

    @Autowired CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    public void whenNoCustomersExist_ThenReturnEmptyList() {
        //arrange
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<CustomerResponseModel>> customerResponseEntity = customerController.getAllCustomers();

        //assert
        assertNotNull(customerResponseEntity);
        assertEquals(customerResponseEntity.getStatusCode(), HttpStatus.OK);
        assertArrayEquals(customerResponseEntity.getBody().toArray(), new ArrayList<CustomerResponseModel>().toArray());
        verify(customerService, times(1)).getAllCustomers();
    }
}
