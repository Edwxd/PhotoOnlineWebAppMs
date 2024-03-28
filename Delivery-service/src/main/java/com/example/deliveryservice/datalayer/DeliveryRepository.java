package com.example.deliveryservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    Delivery findByDeliveryIdentifier_DeliveryId(String delivery);
}
