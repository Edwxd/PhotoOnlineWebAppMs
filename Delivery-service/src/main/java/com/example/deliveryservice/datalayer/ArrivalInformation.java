package com.example.deliveryservice.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor
@Getter
public class ArrivalInformation {

    private String date;
    private String time;

    public ArrivalInformation(@NotNull String date, @NotNull String time) {
        this.date = date;
        this.time = time;
    }

}
