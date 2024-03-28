package com.example.deliveryservice.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryResponseModel extends RepresentationModel<DeliveryResponseModel> {

    private String deliveryId;
    private String company;
    private String delivery_Instructions;
    private String date;
    private String time;
}
