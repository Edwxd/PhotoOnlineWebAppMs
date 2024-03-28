package com.example.deliveryservice.datamapper;

import com.example.deliveryservice.datalayer.Delivery;
import com.example.deliveryservice.presentationlayer.DeliveryController;
import com.example.deliveryservice.presentationlayer.DeliveryResponseModel;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface DeliveryResponseMapper {

  /*  @Mapping(expression = "java(delivery.getDeliveryIdentifier().getDeliveryId())", target = "deliveryId")
    @Mapping(expression = "java(delivery.getArrivalInformation().getDate())", target = "date")
    @Mapping(expression = "java(delivery.getArrivalInformation().getTime())", target = "time")*/
    @Mapping(target = "deliveryId", expression = "java(delivery.getDeliveryIdentifier().getDeliveryId())")
    @Mapping(target = "time", expression = "java(delivery.getArrivalInformation().getTime())")
    @Mapping(target = "date", expression = "java(delivery.getArrivalInformation().getDate())")

  DeliveryResponseModel entityToResponseModel(Delivery delivery);

    List<DeliveryResponseModel> entityListToResponseModelList(List<Delivery> delivery);


    @AfterMapping
    default void addLinks(@MappingTarget DeliveryResponseModel deliveryModel, Delivery delivery){

        Link selfLink = linkTo(methodOn(DeliveryController.class)
                .getDeliveryByDeliveryId(deliveryModel.getDeliveryId()))
                .withSelfRel();

        deliveryModel.add(selfLink);

        Link allDeliveriesLink = linkTo(methodOn(DeliveryController.class).getAllDeliveries()).withRel("All Deliveries");

        deliveryModel.add(allDeliveriesLink);


    }


}
