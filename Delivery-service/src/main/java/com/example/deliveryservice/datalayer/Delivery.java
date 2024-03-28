package com.example.deliveryservice.datalayer;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "deliveries")
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private DeliveryIdentifier deliveryIdentifier;

    @Embedded
    private ArrivalInformation arrivalInformation;

    @Enumerated(EnumType.STRING)
    private Companies company;

    private String delivery_Instructions;

    public Delivery(ArrivalInformation arrivalInformation, Companies company,
                    String delivery_Instructions) {

        this.deliveryIdentifier = new DeliveryIdentifier();
        this.arrivalInformation = arrivalInformation;
        this.company = company;
        this.delivery_Instructions = delivery_Instructions;
    }

    public Delivery() {

    }
}
