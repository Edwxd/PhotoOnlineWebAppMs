package com.example.photoservice.datamapperlayer;


import com.example.photoservice.datalayer.Photo;
import com.example.photoservice.datalayer.PhotoIdentifier;
import com.example.photoservice.presentationlayer.PhotoRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface PhotoRequestMapper {

  @Mappings(value = {
          @Mapping(target = "id", ignore = true),
          //@Mapping(target = "photoIdentifier"),
  })
  Photo requestPhotoToEntity(PhotoRequestModel photoRequestModel, PhotoIdentifier photoIdentifier);

}
