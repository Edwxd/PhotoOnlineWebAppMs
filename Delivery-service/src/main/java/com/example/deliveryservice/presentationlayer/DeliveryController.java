package com.example.deliveryservice.presentationlayer;


import com.example.deliveryservice.buisnesslayer.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping()
    public ResponseEntity<List<DeliveryResponseModel>> getAllDeliveries(){

        return ResponseEntity.ok().body(deliveryService.getAllDelivery());
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseModel> getDeliveryByDeliveryId(@PathVariable String deliveryId){
        return ResponseEntity.ok().body(deliveryService.getDeliveryByDeliveryId(deliveryId));
    }

    @PostMapping()
    public ResponseEntity<DeliveryResponseModel> addDelivery(@RequestBody DeliveryRequestModel deliveryRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.addDelivery(deliveryRequestModel));
    }

    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseModel> updateDelivery(@RequestBody DeliveryRequestModel deliveryRequestModel
            ,@PathVariable String deliveryId){

        return ResponseEntity.ok().body(deliveryService.updateDelivery(deliveryRequestModel, deliveryId));
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable String deliveryId){

        deliveryService.removeDelivery(deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
