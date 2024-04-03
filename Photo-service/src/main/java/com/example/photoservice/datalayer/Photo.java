package com.example.photoservice.datalayer;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "photos") //table needs to be created
@Data
@Getter
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private PhotoIdentifier photoIdentifier;

    private String dimensions;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private FramingOptions framing;

    private Boolean gift_wrap;


    public Photo(String photoIdentifier){ //empty constructor

    }

    public Photo(String dimensions, Color color,
                 FramingOptions framing, Boolean gift_wrap) {

        this.photoIdentifier = new PhotoIdentifier();
        this.dimensions = dimensions;
        this.color = color;
        this.framing = framing;
        this.gift_wrap = gift_wrap;
    }
}
