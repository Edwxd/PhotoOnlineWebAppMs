package com.example.photoservice.presentationlayer;


import com.example.photoservice.buisnesslayer.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private PhotoService photoService;

    public PhotoController(PhotoService photoService) {

        this.photoService = photoService;
    }

    @GetMapping()
    public ResponseEntity<List<PhotoResponseModel>> getAllPhotos(){
        return ResponseEntity.ok().body(photoService.getAllPhotos());
    }

    @GetMapping(value = "/{photoId}"/*, produces = "application/json"*/)
    public ResponseEntity<PhotoResponseModel>getPhotoByPhotoId(@PathVariable String photoId){
        return ResponseEntity.ok().body(photoService.getPhotoByPhotoId(photoId));
    }

    @PostMapping(/*produces = "application/json", consumes = "application/json"*/)
    public ResponseEntity<PhotoResponseModel>addPhoto(@RequestBody PhotoRequestModel photoRequestModel){
        PhotoResponseModel photoResponseModel = photoService.addPhoto(photoRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoResponseModel);
    }

    @PutMapping(value = "/{photoId}"/*, produces = "application/json", consumes = "application/json"*/)
    public ResponseEntity<PhotoResponseModel> updatePhoto(@RequestBody PhotoRequestModel photoRequestModel,
                                                          @PathVariable String photoId){
        PhotoResponseModel photoResponseModel = photoService.updatePhoto(photoRequestModel, photoId);
        return ResponseEntity.status(HttpStatus.OK).body(photoResponseModel);
    }

    @DeleteMapping("/{photoId}")
        public ResponseEntity<Void> deletePhoto(@PathVariable String photoId){
        photoService.deletePhoto(photoId);
        return  ResponseEntity.noContent().build();
    }
}
