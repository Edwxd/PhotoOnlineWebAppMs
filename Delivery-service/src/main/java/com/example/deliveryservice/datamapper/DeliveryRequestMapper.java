package com.example.deliveryservice.datamapper;

import com.example.deliveryservice.datalayer.ArrivalInformation;
import com.example.deliveryservice.datalayer.Delivery;
import com.example.deliveryservice.datalayer.DeliveryIdentifier;
import com.example.deliveryservice.presentationlayer.DeliveryRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DeliveryRequestMapper {

    @Mappings({

            @Mapping(target = "id", ignore = true)
    })
    Delivery requestModelToEntity(DeliveryRequestModel deliveryRequestModel, DeliveryIdentifier deliveryIdentifier,
                                  ArrivalInformation arrivalInformation);
}
