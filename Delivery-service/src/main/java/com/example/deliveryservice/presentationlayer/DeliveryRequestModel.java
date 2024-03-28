package com.example.deliveryservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class DeliveryRequestModel  {

    private String company;
    private String delivery_Instructions;
    private String date;
    private String time;


}
