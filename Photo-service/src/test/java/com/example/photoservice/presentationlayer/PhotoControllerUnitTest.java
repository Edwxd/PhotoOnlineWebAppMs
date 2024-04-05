package com.example.photoservice.presentationlayer;


import com.example.photoservice.buisnesslayer.PhotoService;
import com.example.photoservice.datalayer.FramingOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;


import static com.example.photoservice.datalayer.FramingOptions.METALLIC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PhotoController.class)
public class PhotoControllerUnitTest {

    private final String VALID_PHOTO_ID = "c5040a89-cb47-4c96-888e-ff96708db6f3";

    @Autowired
    PhotoController photoController;

    @MockBean
    private PhotoService photoService;

    @Test
    public void whenPhotosExist_ThenReturnEmptyList(){

        //arrange
        when(photoService.getAllPhotos()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<PhotoResponseModel>> photoResponseEntity = photoController.getAllPhotos();


        //assert
        assertNotNull(photoResponseEntity);
        assertEquals(photoResponseEntity.getStatusCode(), HttpStatus.OK);
        assertArrayEquals(photoResponseEntity.getBody().toArray(), new ArrayList<PhotoResponseModel>().toArray());
        verify(photoService, times(1)).getAllPhotos();
    }


    @Test
    public void whenPhotoExists_ThenReturnPhoto(){
        //arrange
        PhotoResponseModel photoResponseModel = buildPhotoResponseModel();
        List<PhotoResponseModel> photoResponseModelList =
                new ArrayList<PhotoResponseModel>(Arrays.asList(photoResponseModel, photoResponseModel));

        when(photoService.getAllPhotos()).thenReturn(photoResponseModelList);


        //act
        ResponseEntity<List<PhotoResponseModel>> photoResponseEntity =
                photoController.getAllPhotos();

        //assert
        assertNotNull(photoResponseEntity);
        assertEquals(photoResponseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(photoResponseEntity.getBody());
        assertEquals(photoResponseEntity.getBody().size(), 2);
        assertArrayEquals(photoResponseEntity.getBody().toArray(), photoResponseModelList.toArray());
        verify(photoService,  times(1)).getAllPhotos();

    }

    @Test
    public void whenPhotoExits_thenAddPhoto(){
        //arrange
        PhotoRequestModel photoRequestModel = buildPhotoRequestModel("99x99");
        PhotoResponseModel photoResponseModel = buildPhotoResponseModel("99x99");

        when(photoService.addPhoto(photoRequestModel)).thenReturn(photoResponseModel);

        //act
        ResponseEntity<PhotoResponseModel> photoResponseModelResponseEntity
                = photoController.addPhoto(photoRequestModel);

        //assert
        assertNotNull(photoResponseModelResponseEntity);
        assertEquals(HttpStatus.CREATED, photoResponseModelResponseEntity.getStatusCode());
        assertNotNull(photoResponseModelResponseEntity.getBody());
        assertEquals(photoResponseModel, photoResponseModelResponseEntity.getBody());
        verify(photoService, times(1)).addPhoto(photoRequestModel);
    }

    @Test
    public void whenPhotoExits_ThenReturnUpdatedPhoto(){

        //arrange
        PhotoRequestModel photoRequestModel = buildPhotoRequestModel("55x55");
        PhotoResponseModel photoResponseModel = buildPhotoResponseModel("55x55");

        when(photoService.updatePhoto(photoRequestModel, VALID_PHOTO_ID)).thenReturn(photoResponseModel);


        //act
        ResponseEntity<PhotoResponseModel> photoResponseModelResponseEntity
                = photoController.updatePhoto(photoRequestModel, VALID_PHOTO_ID);

        //Assert
        assertNotNull(photoResponseModelResponseEntity);
        assertEquals(photoResponseModelResponseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(photoResponseModelResponseEntity.getBody());
        assertEquals(photoResponseModelResponseEntity.getBody(), photoResponseModel);
        verify(photoService, times(1)).updatePhoto(photoRequestModel, VALID_PHOTO_ID);
    }


    @Test
    public void whenPhotoExists_ThenUpdatePhoto(){

        //Arrange

        PhotoRequestModel photoRequestModel = buildPhotoRequestModel("10x10");
        PhotoResponseModel photoResponseModel = buildPhotoResponseModel("10x10");

        when(photoService.updatePhoto(photoRequestModel, String.valueOf(UUID.fromString(VALID_PHOTO_ID))))
                .thenReturn(photoResponseModel);

        //act
        ResponseEntity<PhotoResponseModel> photoResponseModelResponseEntity =
                photoController.updatePhoto(photoRequestModel,String.valueOf(UUID.fromString(VALID_PHOTO_ID)));

        //assert
        assertNotNull(photoResponseModelResponseEntity);
        assertEquals(HttpStatus.OK, photoResponseModelResponseEntity.getStatusCode());
        assertNotNull(photoResponseModelResponseEntity.getBody());
        assertEquals(photoResponseModel, photoResponseModelResponseEntity.getBody());
        verify(photoService, times(1)).updatePhoto(photoRequestModel,
                String.valueOf(UUID.fromString(VALID_PHOTO_ID)));


    }

    @Test
    public void whenPhotoExists_ThenDeletePhoto(){

        //Arrange
        doNothing().when(photoService).deletePhoto(VALID_PHOTO_ID);


        //act
        ResponseEntity<Void> photoResponseModelResponseEntity =
                photoController.deletePhoto(VALID_PHOTO_ID);


        //Assert
        assertNotNull(photoResponseModelResponseEntity);
        assertEquals(photoResponseModelResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(photoService, times(1)).deletePhoto(VALID_PHOTO_ID);
    }



    private PhotoResponseModel buildPhotoResponseModel(){

        return PhotoResponseModel.builder()
                .photoId(VALID_PHOTO_ID)
                .dimensions("8x10")
                .color("NO_COLOR")
                .framing("WOODEN_WHITE")
                .gift_wrap(false)
                .build();
    }

    private PhotoResponseModel buildPhotoResponseModel(String dimensions){

        return PhotoResponseModel.builder()
                .photoId(VALID_PHOTO_ID)
                .dimensions(dimensions)
                .color("NO_COLOR")
                .framing("WOODEN_WHITE")
                .gift_wrap(false)
                .build();
    }

    private PhotoRequestModel buildPhotoRequestModel(String dimensions){

        return PhotoRequestModel.builder()
                .dimensions(dimensions)
                .color("NO_COLOR")
                .framing("WOODEN_WHITE")
                .gift_wrap(false)
                .build();
    }



}
