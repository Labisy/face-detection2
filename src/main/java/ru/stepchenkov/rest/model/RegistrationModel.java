package ru.stepchenkov.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationModel {
    private String firstName;
    private String lastName;
    private String thirdName;
    private Integer serviceNumber;
    private Integer dep;
    private byte[] photo;
    private LocalDate date;
    private String postName;
    private String phone;

}
