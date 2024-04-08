package com.example.photoservice.datalayer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PhotoRepositoryIntegrationTest {

    @Autowired
    private PhotoRepository photoRepository;


    @BeforeEach
    public void setupDatabase(){


        photoRepository.deleteAll();
    }


    @Test
    public void whenPhotoExists_ReturnPhotoByPhotoId(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);
        photoRepository.save(photo1);

        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(photo1.getPhotoIdentifier().getPhotoId());

        //assert
        assertNotNull(photo);
        assertEquals(photo.getPhotoIdentifier(), photo1.getPhotoIdentifier());

        //EXPECT VS ACTUAL
        assertEquals(photo.getDimensions(), photo1.getDimensions());
        assertEquals(photo.getColor(), photo1.getColor());

    }


    @Test
    public void whenPhotoDoesNotExist_ReturnNull(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);

        photoRepository.save(photo1);

        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId("05c8ab76-4f75-45c1-b6e2-aa8e91488888");

        //assert
        assertNull(photo);
    }


    @Test
    public void whenPhotoIdIsInvalid_ReturnNull(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);

        photoRepository.save(photo1);


        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId("05c8ab76-4f75-45c1-b6e2");

        assertNull(photo);
    }


    @Test
    public void whenPhotoIdIsNull_ReturnNull(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);

        photoRepository.save(photo1);


        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(null);

        assertNull(photo);
    }




    @Test
    public void whenPhotoIdIsEmpty_ReturnNull(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);

        photoRepository.save(photo1);


        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId("");

        assertNull(photo);
    }


    @Test
    public void whenPhotoIdIsBlank_ReturnNull(){

        //arrange
        Photo photo1 = new Photo("25x50",Color.NO_COLOR, FramingOptions.WOODEN_BLACK, true);

        photoRepository.save(photo1);


        //act
        Photo photo = photoRepository.findByPhotoIdentifier_PhotoId(" ");

        assertNull(photo);
    }



}
