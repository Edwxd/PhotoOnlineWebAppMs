package com.example.deliveryservice.buisnesslayer;


import com.example.deliveryservice.datalayer.ArrivalInformation;
import com.example.deliveryservice.datalayer.Delivery;
import com.example.deliveryservice.datalayer.DeliveryIdentifier;
import com.example.deliveryservice.datalayer.DeliveryRepository;
import com.example.deliveryservice.datamapper.DeliveryRequestMapper;
import com.example.deliveryservice.datamapper.DeliveryResponseMapper;
import com.example.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.example.deliveryservice.presentationlayer.DeliveryResponseModel;
import com.example.deliveryservice.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryRequestMapper deliveryRequestMapper;

    private final DeliveryResponseMapper deliveryResponseMapper;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               DeliveryRequestMapper deliveryRequestMapper,
                               DeliveryResponseMapper deliveryResponseMapper) {

        this.deliveryRepository = deliveryRepository;
        this.deliveryRequestMapper = deliveryRequestMapper;
        this.deliveryResponseMapper = deliveryResponseMapper;
    }


    @Override
    public List<DeliveryResponseModel> getAllDelivery() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveryResponseMapper.entityListToResponseModelList(deliveries);
    }

    @Override
    public DeliveryResponseModel getDeliveryByDeliveryId(String deliveryId) {
        Delivery delivery = deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId);

        if(delivery == null){

            throw new NotFoundException("Unknown deliveryId: " + deliveryId);
        }

        return deliveryResponseMapper.entityToResponseModel(delivery);
    }

    @Override
    public DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel) {
        ArrivalInformation arrivalInformation = new ArrivalInformation(deliveryRequestModel.getDate()
                , deliveryRequestModel.getTime());

        Delivery delivery = deliveryRequestMapper.requestModelToEntity(deliveryRequestModel, new DeliveryIdentifier()
                , arrivalInformation);

        delivery.setArrivalInformation(arrivalInformation);
        return deliveryResponseMapper.entityToResponseModel(deliveryRepository.save(delivery));
    }

    @Override
    public DeliveryResponseModel updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId) {
        Delivery existingDelivery = deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId);

        if(existingDelivery == null){

            throw new NotFoundException("Unknown deliveryId: " + deliveryId);
        }

        ArrivalInformation arrivalInformation = new ArrivalInformation(deliveryRequestModel.getDate()
                , deliveryRequestModel.getTime());

        Delivery updatedDelivery = deliveryRequestMapper.requestModelToEntity(deliveryRequestModel,
                existingDelivery.getDeliveryIdentifier(), arrivalInformation);
        updatedDelivery.setId(existingDelivery.getId());

        Delivery response = deliveryRepository.save(updatedDelivery);
        return deliveryResponseMapper.entityToResponseModel(response);
    }

    @Override
    public void removeDelivery(String deliveryId) {

        Delivery existingDelivery = deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId);

        if(existingDelivery == null){

            throw new NotFoundException("Unknown deliveryId: " + deliveryId);
        }

        deliveryRepository.delete(existingDelivery);
    }
}
