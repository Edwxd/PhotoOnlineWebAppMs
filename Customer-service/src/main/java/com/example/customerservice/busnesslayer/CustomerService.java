package com.example.customerservice.busnesslayer;


import com.example.customerservice.presentationlayer.CustomerRequestModel;
import com.example.customerservice.presentationlayer.CustomerResponseModel;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseModel> getAllCustomers();

    CustomerResponseModel getCustomerByCustomerId(String customerId);

    CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel);

    CustomerResponseModel updateCustomer(CustomerRequestModel updatedCustomer, String customerId);

    void removeCustomer(String customerId);
}
