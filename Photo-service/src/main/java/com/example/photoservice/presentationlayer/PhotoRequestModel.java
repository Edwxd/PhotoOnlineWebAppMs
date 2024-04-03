package com.example.photoservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoRequestModel {

    private String dimensions;
    private String color;
    private String framing;
    private Boolean gift_wrap;
}
