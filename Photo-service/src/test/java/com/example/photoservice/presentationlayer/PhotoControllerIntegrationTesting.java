package com.example.photoservice.presentationlayer;

import com.example.photoservice.datalayer.Photo;
import com.example.photoservice.datalayer.PhotoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PhotoControllerIntegrationTesting {

    private final String BASE_URI_PHOTOS = "/api/v1/photos";

    private final String FOUND_PHOTO_ID = "c5040a89-cb47-4c96-888e-ff96708db6f3";

    private final String FOUND_PHOTO_DIMENSIONS = "8x10";

    private final String NOT_FOUND_PHOTO_ID = "c5040a89-yr35-7628-888e-ff96708db6f3";


    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void whenGetPhotos_thenReturnAllPhotos(){

        Long sizeDatabase = photoRepository.count();

        //System.out.println("This is the size of the data base:" + sizeDatabase);
        //count the number of customers in the database

        webTestClient.get()
                .uri(BASE_URI_PHOTOS) //what is the uri ?
                .accept(MediaType.APPLICATION_JSON)// web client is sending this type of media
                .exchange()// send the request
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON) // return type JSON
                .expectBodyList(PhotoResponseModel.class)// expect the return of the list
                .value((list)-> {

                    //assert
                    assertNotNull(list); //always check if the value expected is not null
                    assertTrue(list.size() == sizeDatabase);// check if the list size is = to teh db size
                });
    }


    @Test
    public void whenPhotoById_thenReturnPhoto(){

        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(FOUND_PHOTO_ID);

        System.out.println(BASE_URI_PHOTOS + "/" + FOUND_PHOTO_ID);

        webTestClient.get()
                .uri(BASE_URI_PHOTOS + "/" + FOUND_PHOTO_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PhotoResponseModel.class)
                .value((response) -> {

                    //assert
                    assertNotNull(response);
                    assertEquals(response.getPhotoId(), FOUND_PHOTO_ID);
                    assertEquals(response.getDimensions(), FOUND_PHOTO_DIMENSIONS);
                });
    }


    @Test
    public void whenGetPhotoDoesNotExist_thenReturnNotFound(){

        webTestClient.get()
                .uri(BASE_URI_PHOTOS + "/" + NOT_FOUND_PHOTO_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown photoId: " + NOT_FOUND_PHOTO_ID);
    }


    @Test
    public void whenValidPhoto_thenCreatePhoto(){

        long sizeDatabase = photoRepository.count();

        PhotoRequestModel photoRequestModel = new PhotoRequestModel("99x99", "NO_COLOR",
                "METALLIC", false);


        //act and assert
        webTestClient.post()
                .uri(BASE_URI_PHOTOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(photoRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.parseMediaType("application/hal+json"))
                .expectBody(PhotoResponseModel.class)
                .value((photoResponseModel) -> {

                    assertNotNull(photoResponseModel);
                    assertEquals(photoResponseModel.getDimensions(), photoRequestModel.getDimensions());
                    assertEquals(photoResponseModel.getColor(), photoRequestModel.getColor());
                    assertEquals(photoResponseModel.getFraming(), photoRequestModel.getFraming());
                    assertEquals(photoResponseModel.getGift_wrap(), photoRequestModel.getGift_wrap());



                });

                long sizeDBAfterChanges = photoRepository.count();
                assertEquals(sizeDatabase + 1, sizeDBAfterChanges );
    }



    @Test
    public void whenPhotoExists_thenUpdatePhoto(){

        long sizeDatabase = photoRepository.count();
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(FOUND_PHOTO_ID);

        PhotoRequestModel photoRequestModel = new PhotoRequestModel("99x99", "NO_COLOR",
                "METALLIC", false);


        //act and assert
        webTestClient.put()
                .uri(BASE_URI_PHOTOS + "/" + FOUND_PHOTO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(photoRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.parseMediaType("application/hal+json"))
                .expectBody(PhotoResponseModel.class)
                .value((photoResponseModel) -> {

                    assertNotNull(photoResponseModel);
                    assertEquals(photoResponseModel.getDimensions(), photoRequestModel.getDimensions());
                    assertEquals(photoResponseModel.getColor(), photoRequestModel.getColor());
                    assertEquals(photoResponseModel.getFraming(), photoRequestModel.getFraming());
                    assertEquals(photoResponseModel.getGift_wrap(), photoRequestModel.getGift_wrap());

                });

        long sizeDBAfterChanges = photoRepository.count();
        assertEquals(sizeDatabase, sizeDBAfterChanges);

    }



    @Test
    public void whenUpdatePhotoDoesNotExist_thenThrowException(){

        PhotoRequestModel photoRequestModel = new PhotoRequestModel("99x99", "NO_COLOR",
                "METALLIC", false);

        webTestClient.put()
                .uri(BASE_URI_PHOTOS + "/" + NOT_FOUND_PHOTO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(photoRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No photo found with Id: " + NOT_FOUND_PHOTO_ID);
    }


    @Test
    public void whenPhotoExists_theDeletePhoto(){

        long sizeDatabase = photoRepository.count();

        webTestClient.delete()
                .uri(BASE_URI_PHOTOS + "/" + FOUND_PHOTO_ID)
                .exchange()
                .expectStatus().isNoContent();

        long sizeDBAfterChanges = photoRepository.count();
        assertEquals(sizeDatabase - 1, sizeDBAfterChanges);

    }



    @Test
    public void whenPhotoDoesNotExists_theThrowException(){



        webTestClient.delete()
                .uri(BASE_URI_PHOTOS + "/" + NOT_FOUND_PHOTO_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No photo found with Id: " + NOT_FOUND_PHOTO_ID);





    }




}
