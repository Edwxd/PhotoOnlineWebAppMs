package com.example.photoservice.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class PhotoResponseModel extends RepresentationModel<PhotoResponseModel> {

    private String photoId;

    private String dimensions;
    private String color;
    private String framing;
    private Boolean gift_wrap;

}
