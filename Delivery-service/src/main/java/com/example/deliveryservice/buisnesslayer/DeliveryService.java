package com.example.deliveryservice.buisnesslayer;


import com.example.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.example.deliveryservice.presentationlayer.DeliveryResponseModel;

import java.util.List;

public interface DeliveryService {

    List<DeliveryResponseModel> getAllDelivery();

    DeliveryResponseModel getDeliveryByDeliveryId(String deliveryId);

    DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel);

    DeliveryResponseModel updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId);

    void removeDelivery(String deliveryId);


}
