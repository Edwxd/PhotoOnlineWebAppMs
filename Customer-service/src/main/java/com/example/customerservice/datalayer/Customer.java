package com.example.customerservice.datalayer;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CustomerIdentifier customerIdentifier;


    private String firstName;
    private String lastName;
    private String email;


    @Embedded
    private Address address;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "customer_phonenumbers", joinColumns = @JoinColumn(name = "customer_id"))
    private List<PhoneNumberInformation> phoneNumbers;

    public Customer(@NotNull String firstName, @NotNull String lastName, @NotNull String email,
                    @NotNull Address address, @NotNull List<PhoneNumberInformation> phoneNumbersList) {


        this.customerIdentifier = new CustomerIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumbers = phoneNumbersList;
    }


}
