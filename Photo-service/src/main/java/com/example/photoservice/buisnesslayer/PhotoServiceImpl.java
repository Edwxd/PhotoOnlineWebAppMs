package com.example.photoservice.buisnesslayer;


import com.example.photoservice.datalayer.Photo;
import com.example.photoservice.datalayer.PhotoIdentifier;
import com.example.photoservice.datalayer.PhotoRepository;
import com.example.photoservice.datamapperlayer.PhotoRequestMapper;
import com.example.photoservice.datamapperlayer.PhotoResponseMapper;
import com.example.photoservice.presentationlayer.PhotoRequestModel;
import com.example.photoservice.presentationlayer.PhotoResponseModel;
import com.example.photoservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;

   private PhotoRequestMapper photoRequestMapper;

   private PhotoResponseMapper photoResponseMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository,
                            PhotoRequestMapper photoRequestMapper,
                            PhotoResponseMapper photoResponseMapper) {

        this.photoRepository = photoRepository;
        this.photoRequestMapper = photoRequestMapper;
        this.photoResponseMapper = photoResponseMapper;
    }

    @Override
    public List<PhotoResponseModel> getAllPhotos(){
        List<Photo>photos = photoRepository.findAll();
        return  photoResponseMapper.enityListToResponseModelList(photos);
    }

    @Override
    public PhotoResponseModel getPhotoByPhotoId(String photoId){

        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(photoId);


        if(photo == null){

            throw new NotFoundException("Unknown photoId: " + photoId);
        }

        return photoResponseMapper.entityToResponseModel(photo);
    }


    @Override
    public PhotoResponseModel addPhoto(PhotoRequestModel photoRequestModel){

        Photo photo = photoRequestMapper.requestPhotoToEntity(photoRequestModel, new PhotoIdentifier());
        return photoResponseMapper.entityToResponseModel(photoRepository.save(photo));
    }

    @Override
    public PhotoResponseModel updatePhoto(PhotoRequestModel photoRequestModel, String photoId){
        Photo existingPhoto = photoRepository.findByPhotoIdentifier_PhotoId(photoId);

        if(existingPhoto == null){


            throw  new NotFoundException("No photo found with Id: " + photoId);

        }

        Photo updatedPhoto = photoRequestMapper.requestPhotoToEntity(photoRequestModel, existingPhoto.getPhotoIdentifier());
        updatedPhoto.setId(existingPhoto.getId());

        Photo response = photoRepository.save(updatedPhoto);
        return photoResponseMapper.entityToResponseModel(response);
    }

    @Override
    public void deletePhoto(String photoId){

        Photo existingPhoto = photoRepository.findByPhotoIdentifier_PhotoId(photoId);

        if(existingPhoto == null){

            throw new NotFoundException("No photo found with Id: " + photoId);
        }

        photoRepository.delete(existingPhoto);
    }
}
