package com.example.customerservice.presentationlayer;

import com.example.customerservice.busnesslayer.CustomerService;


import com.example.customerservice.datalayer.PhoneNumberInformation;
import com.example.customerservice.datalayer.PhoneNumberType;
import com.example.customerservice.utils.exceptions.InvalidInputException;
import com.example.customerservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = CustomerController.class)
public class CustomerControllerUnitTest {


    private final String VALID_CUSTOMER_ID = "7f5cf6c6-031b-4e5b-bb28-f83b0b7c57b0";
    private final String NOT_FOUND_CUSTOMER_ID = "6l2cf6c6-031b-4e5b-bb28-f83b0b7c57b0";

    private final String INVALID_CUSTOMER_ID = "7f5cf6c6-031b-4e5b-bb28-f83b0b7c57b";

    @Autowired CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    public void whenNoCustomersExist_ThenReturnEmptyList() { //test to return a empty list
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


    @Test
    public void whenCustomerExists_ThenReturnCustomer(){ //test to return a customer

        //arrange
        CustomerResponseModel customerResponseModel = buildCustomerResponseModel();
        List<CustomerResponseModel> customerResponseModelList =
                new ArrayList<CustomerResponseModel>(Arrays.asList(customerResponseModel, customerResponseModel));

        when(customerService.getAllCustomers()).thenReturn(customerResponseModelList);


        //act
        ResponseEntity<List<CustomerResponseModel>> customerResponseEntity = customerController.getAllCustomers();

        //assert
        assertNotNull(customerResponseEntity);
        assertEquals(customerResponseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(customerResponseEntity.getBody());
        assertEquals(customerResponseEntity.getBody().size(), 2);
        assertArrayEquals(customerResponseEntity.getBody().toArray(), customerResponseModelList.toArray());
        verify(customerService, times(1)).getAllCustomers();

    }


    @Test
    public void whenMemberExists_thenAddCustomer() {  //test to add a customer
        //arrange
        CustomerRequestModel customerRequestModel = buildCustomerRequestModel("Emma");
        CustomerResponseModel customerResponseModel = buildCustomerResponseModel("Emma");

        when(customerService.addCustomer(customerRequestModel)).thenReturn(customerResponseModel);

        //act
        ResponseEntity<CustomerResponseModel> responseEntity = customerController.addCustomer(customerRequestModel);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(customerResponseModel, responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(customerRequestModel);

    }


    @Test
    public void whenCustomerExists_ThenReturnUpdatedCustomer() { //test to return updatedC customer
        //arrange
        CustomerRequestModel customerRequestModel = buildCustomerRequestModel("Betty");
        CustomerResponseModel customerResponseModel = buildCustomerResponseModel("Betty");
        when(customerService.updateCustomer(customerRequestModel, VALID_CUSTOMER_ID)).thenReturn(customerResponseModel);

        //act
        ResponseEntity<CustomerResponseModel> customerResponseEntity = customerController.updateCustomer(customerRequestModel, VALID_CUSTOMER_ID);

        //assert
        assertNotNull(customerResponseEntity);
        assertEquals(customerResponseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(customerResponseEntity.getBody());
        assertEquals(customerResponseEntity.getBody(), customerResponseModel);
        verify(customerService, times(1)).updateCustomer(customerRequestModel, VALID_CUSTOMER_ID);
    }


    @Test
    public void whenCustomerExist_thenUpdateCustomer(){ //test to update customer

        CustomerRequestModel customerRequestModel = buildCustomerRequestModel("Edward");
        CustomerResponseModel customerResponseModel = buildCustomerResponseModel("Edward");

        when(customerService.updateCustomer(customerRequestModel, String.valueOf(UUID.fromString(VALID_CUSTOMER_ID))))
                .thenReturn(customerResponseModel);

        //act
        when(customerService.updateCustomer(customerRequestModel, String.valueOf(UUID.fromString(VALID_CUSTOMER_ID))))
                .thenReturn(customerResponseModel);

     //act
     ResponseEntity<CustomerResponseModel> responseEntity =
             customerController.updateCustomer(customerRequestModel, String.valueOf(UUID.fromString(VALID_CUSTOMER_ID)));

     //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(customerResponseModel, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(customerRequestModel, String.valueOf(UUID.fromString(VALID_CUSTOMER_ID)));
    }


    @Test
    public void whenCustomerExists_ThenDeleteCustomer() { //test to delete customer
        //arrange
        doNothing().when(customerService).removeCustomer(VALID_CUSTOMER_ID);

        //act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(VALID_CUSTOMER_ID);

        //assert
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(customerService, times(1)).removeCustomer(VALID_CUSTOMER_ID);
    }





    private CustomerResponseModel buildCustomerResponseModel() {
        List<PhoneNumberInformation> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.PERSONEl, "415-555-1212"));
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.WORK, "415-555-1213"));

        return CustomerResponseModel.builder()
                .customerId(VALID_CUSTOMER_ID)
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .streetAddress("12 Main St")
                .city("St. Denis")
                .state("Quebec")
                .country("Canada")
                .postalCode("J2E5T6")
                .phoneNumbers(phoneNumberList)
                .build();
    }

    private CustomerResponseModel buildCustomerResponseModel(String firstName) {
        List<PhoneNumberInformation> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.PERSONEl, "415-555-1212"));
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.WORK, "415-555-1213"));

        return CustomerResponseModel.builder()
                .customerId(VALID_CUSTOMER_ID)
                .firstName(firstName)
                .lastName("Doe")
                .email("john@gmail.com")
                .streetAddress("12 Main St")
                .city("St. Denis")
                .state("Quebec")
                .country("Canada")
                .postalCode("J2E5T6")
                .phoneNumbers(phoneNumberList)
                .build();
    }

    private CustomerRequestModel buildCustomerRequestModel() {
        List<PhoneNumberInformation> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.WORK, "415-555-1212"));
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.PERSONEl, "415-555-1213"));

        return CustomerRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .streetAddress("12 Main St")
                .city("St. Denis")
                .state("Quebec")
                .country("Canada")
                .postalCode("J2E5T6")
                .phoneNumbers(phoneNumberList)
                .build();
    }

    private CustomerRequestModel buildCustomerRequestModel(String firstName) {
        List<PhoneNumberInformation> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.HOME, "415-555-1212"));
        phoneNumberList.add(new PhoneNumberInformation(PhoneNumberType.WORK, "415-555-1213"));

        return CustomerRequestModel.builder()
                .firstName(firstName)
                .lastName("Doe")
                .email("john@gmail.com")
                .streetAddress("12 Main St")
                .city("St. Denis")
                .state("Quebec")
                .country("Canada")
                .postalCode("J2E5T6")
                .phoneNumbers(phoneNumberList)
                .build();
    }


}





