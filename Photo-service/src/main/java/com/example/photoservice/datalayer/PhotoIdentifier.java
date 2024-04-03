package com.example.photoservice.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class PhotoIdentifier {

    private String photoId;

    public PhotoIdentifier(){

        this.photoId = UUID.randomUUID().toString();
    }
}
