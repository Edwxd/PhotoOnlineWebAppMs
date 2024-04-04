package com.example.customerservice.presentationlayer;

import com.example.customerservice.datalayer.PhoneNumberInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class CustomerResponseModel extends RepresentationModel<CustomerResponseModel> {

    private String customerId;
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
