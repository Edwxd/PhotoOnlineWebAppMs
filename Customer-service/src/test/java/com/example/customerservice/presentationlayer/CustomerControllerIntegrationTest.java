package com.example.customerservice.presentationlayer;

import com.example.customerservice.datalayer.*;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class CustomerControllerIntegrationTest {

    private final String BASE_URI_CUSTOMERS = "/api/v1/customers";
    private final String FOUND_CUSTOMERS_ID = "7f5cf6c6-031b-4e5b-bb28-f83b0b7c57b0";

    private final String FOUND_CUSTOMER_FIRST_NAME = "John";
    private final String FOUND_CUSTOMER_LAST_NAME = "Doe";

    private final String NOT_FOUND_CUSTOMER_ID = "7f5cf6c6-031b-6e4c-yt23-f83b0b7c57b0";


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    //@SQL("src\\main\\resources\\data-h2.sql")
    public void whenGetCustomers_thenReturnAllCustomers() {
        //arrange
        Long sizeDataBase = customerRepository.count();

        System.out.println("This is the size of the data base:" + sizeDataBase);
        //count the number of customers in the database

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS)//what is the uri?
                .accept(MediaType.APPLICATION_JSON)//web client is sending this type of media
                .exchange()// actually send the request
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)//return type is json
                .expectBodyList(CustomerResponseModel.class)//expect the return of a list
                .value((list) -> {
                    //assert
                    assertNotNull(list); //always check if the value expected is not null
                    assertTrue(list.size() == sizeDataBase); //check if the size of the list is the same as the size of the database
                });
    }

    @Test
    public void whenGetCustomerById_thenReturnCustomer() {
        //arrange
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(FOUND_CUSTOMERS_ID);

        System.out.println(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMERS_ID);
        //act and assert
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMERS_ID)//what is the uri?
                .accept(MediaType.APPLICATION_JSON)//web client is sending this type of media
                .exchange()// actually send the request
                .expectStatus().isOk()
                //-------------404 not found
                .expectHeader().contentType(MediaType.APPLICATION_JSON)//return type is json
                .expectBody(CustomerResponseModel.class)//expect the return of a list
                .value((response) -> {
                    //assert
                    assertNotNull(response); //always check if the value expected is not null
                    assertEquals(response.getCustomerId(), FOUND_CUSTOMERS_ID);
                    assertEquals(response.getFirstName(), FOUND_CUSTOMER_FIRST_NAME);
                    assertEquals(response.getLastName(), FOUND_CUSTOMER_LAST_NAME);
                });
    }

    @Test
    public void whenGetCustomerDoesNotExist_thenReturnNotFound() { //Not working
        //act and assert
        webTestClient.get()
                .uri(BASE_URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)//what is the uri?
                .accept(MediaType.APPLICATION_JSON)//web client is sending this type of media
                .exchange()// actually send the request
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customer id: " + NOT_FOUND_CUSTOMER_ID); //same message as in serviceimpl
    }


    @Test
    public void whenValidCustomer_thenCreateCustomer() { //Not working
        //arrange
        long sizeDatabase = customerRepository.count();
        List<PhoneNumberInformation> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.WORK, "678-736-0980"));
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.HOME, "119-8930-9988"));

        CustomerRequestModel customerRequestModel = new CustomerRequestModel("Edward", "Rock",
                "EdRo@gmail.com", "Croissant Sherwood", "Montreal",
                "QC", "Canada", "3H38L1", phoneNumbers);

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.parseMediaType("application/hal+json"))
                .expectBody(CustomerResponseModel.class)
                .value((customerResponseModel) -> {
                    assertNotNull(customerResponseModel);
                    assertEquals(customerResponseModel.getFirstName(), customerRequestModel.getFirstName());
                    assertEquals(customerResponseModel.getLastName(), customerRequestModel.getLastName());
                    assertEquals(customerResponseModel.getEmail(), customerRequestModel.getEmail());
                    assertEquals(customerResponseModel.getCity(), customerRequestModel.getCity());
                    assertEquals(customerResponseModel.getState(), customerRequestModel.getState());
                    assertEquals(customerResponseModel.getCountry(), customerRequestModel.getCountry());
                    assertEquals(customerResponseModel.getPostalCode(), customerRequestModel.getPostalCode());
                    assertEquals(customerResponseModel.getPhoneNumbers().size(), customerRequestModel.getPhoneNumbers().size());
                });

        long sizeDBAfter = customerRepository.count();
        assertEquals(sizeDatabase + 1, sizeDBAfter);
    }


    @Test
    public void whenCustomerExists_thenUpdateCustomer() {
        //arrange
        long sizeDataBase = customerRepository.count();
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(FOUND_CUSTOMERS_ID);

        List<PhoneNumberInformation> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.WORK, "678-736-0980"));
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.HOME, "119-8930-9988"));

        CustomerRequestModel customerRequestModel = new CustomerRequestModel("Edward", "Rock",
                "EdRo@gmail.com", "Croissant Sherwood", "Montreal",
                "QC", "Canada", "3H38L1", phoneNumbers);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMERS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.parseMediaType("application/hal+json"))
                .expectBody(CustomerResponseModel.class)
                .value((customerResponseModel) -> {
                    assertNotNull(customerResponseModel);
                    assertEquals(customerResponseModel.getFirstName(), customerRequestModel.getFirstName());
                    assertEquals(customerResponseModel.getLastName(), customerRequestModel.getLastName());
                    assertEquals(customerResponseModel.getEmail(), customerRequestModel.getEmail());
                    assertEquals(customerResponseModel.getCity(), customerRequestModel.getCity());
                    assertEquals(customerResponseModel.getState(), customerRequestModel.getState());
                    assertEquals(customerResponseModel.getCountry(), customerRequestModel.getCountry());
                    assertEquals(customerResponseModel.getPostalCode(), customerRequestModel.getPostalCode());
                    assertEquals(customerResponseModel.getPhoneNumbers().size(), customerRequestModel.getPhoneNumbers().size());
                });

        long sizeDBAfter = customerRepository.count();
        assertEquals(sizeDataBase, sizeDBAfter);
    }

    @Test
    public void whenUpdateCustomerDoesNotExist_thenThrowException() { //Not working
        //arrange
        List<PhoneNumberInformation> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.WORK, "678-736-0980"));
        phoneNumbers.add(new PhoneNumberInformation(PhoneNumberType.HOME, "119-8930-9988"));

        CustomerRequestModel customerRequestModel = new CustomerRequestModel("Edward", "Rock",
                "EdRo@gmail.com", "Croissant Sherwood", "Montreal",
                "QC", "Canada", "3H38L1", phoneNumbers);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + NOT_FOUND_CUSTOMER_ID);

    }

    @Test
    public void whenCustomerExists_thenDeleteCustomer() { //Not working
        //arrange
        long sizeDataBase = customerRepository.count();

        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_CUSTOMERS + "/" + FOUND_CUSTOMERS_ID)
                .exchange()
                .expectStatus().isNoContent();

        long sizeDBAfter = customerRepository.count();
        assertEquals(sizeDataBase - 1, sizeDBAfter);
    }

    @Test
    public void whenDeleteCustomerDoesNotExist_thenThrowException() { //Not working
        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_CUSTOMERS + "/" + NOT_FOUND_CUSTOMER_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown customerId: " + NOT_FOUND_CUSTOMER_ID);
    }

}


