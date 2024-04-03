package com.example.photoservice.datamapperlayer;


import com.example.photoservice.datalayer.Photo;
import com.example.photoservice.presentationlayer.PhotoController;
import com.example.photoservice.presentationlayer.PhotoResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;



import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface PhotoResponseMapper {

    @Mapping(target = "photoId", expression = "java(photo.getPhotoIdentifier().getPhotoId())")
    PhotoResponseModel entityToResponseModel(Photo photo);

    List<PhotoResponseModel> enityListToResponseModelList(List<Photo> photos);


    @AfterMapping
    default void addLinks(@MappingTarget PhotoResponseModel photoModel, Photo photo){

        Link selfLink = linkTo(methodOn(PhotoController.class).getPhotoByPhotoId(photoModel.getPhotoId()))
                .withSelfRel();

        photoModel.add(selfLink);

        Link allPhotosLink = linkTo(methodOn(PhotoController.class).getAllPhotos()).withRel("All Photos");

        photoModel.add(allPhotosLink);
    }
}
