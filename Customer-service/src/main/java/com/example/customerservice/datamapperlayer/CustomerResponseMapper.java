package com.example.customerservice.datamapperlayer;


import com.example.customerservice.datalayer.Customer;
import com.example.customerservice.presentationlayer.CustomerController;
import com.example.customerservice.presentationlayer.CustomerResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {


    @Mapping(expression = "java(customer.getCustomerIdentifier().getCustomerId())", target = "customerId")
    @Mapping(expression = "java(customer.getAddress().getStreetAddress())", target = "streetAddress")
    @Mapping(expression = "java(customer.getAddress().getCity())", target = "city")
    @Mapping(expression = "java(customer.getAddress().getState())", target = "state")
    @Mapping(expression = "java(customer.getAddress().getCountry())", target = "country")
    @Mapping(expression = "java(customer.getAddress().getPostalCode())", target = "postalCode")
    CustomerResponseModel entityToResponseModel(Customer customer);

    List<CustomerResponseModel> entityListToResponseModelList(List<Customer> customers);


    @AfterMapping
    default void addLinks(@MappingTarget CustomerResponseModel customerModel, Customer customer){

        Link selfLink = linkTo(methodOn(CustomerController.class).getCustomerByCustomerId(customerModel.getCustomerId()))
                .withSelfRel();

        customerModel.add(selfLink);

        Link allCustomersLink = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("All Customers");

        customerModel.add(allCustomersLink);
    }

}
