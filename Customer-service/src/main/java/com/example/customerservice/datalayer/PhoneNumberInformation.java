package com.example.customerservice.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class PhoneNumberInformation {

    @Enumerated(EnumType.STRING)
    private PhoneNumberType type;
    private String number;

    public PhoneNumberInformation(@NotNull PhoneNumberType type, @NotNull String number) {

        this.type = type;
        this.number = number;
    }


}
