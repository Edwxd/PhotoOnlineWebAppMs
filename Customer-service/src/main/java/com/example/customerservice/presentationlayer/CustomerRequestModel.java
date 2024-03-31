package com.example.customerservice.presentationlayer;

import com.example.customerservice.datalayer.PhoneNumberInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class CustomerRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private List<PhoneNumberInformation> phoneNumbers;
}
