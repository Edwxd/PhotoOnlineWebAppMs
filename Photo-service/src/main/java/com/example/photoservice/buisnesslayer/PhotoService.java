package com.example.photoservice.buisnesslayer;



import com.example.photoservice.presentationlayer.PhotoRequestModel;
import com.example.photoservice.presentationlayer.PhotoResponseModel;

import java.util.List;

public interface PhotoService {

    List<PhotoResponseModel> getAllPhotos();

    PhotoResponseModel getPhotoByPhotoId(String photoId);

    PhotoResponseModel addPhoto(PhotoRequestModel photoRequestModel);

    PhotoResponseModel updatePhoto(PhotoRequestModel photoRequestModel, String photoId);

    void deletePhoto(String photoId);
}
