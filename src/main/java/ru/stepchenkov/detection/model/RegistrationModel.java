package ru.stepchenkov.detection.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrationModel {
    private String firstName;
    private String lastName;
    private String thirdName;
    private Integer serviceNumber;
    private Integer dep;
    private byte[] photoId;
    private LocalDate date;
    private String postName;
    private String phone;

    public RegistrationModel(String firstName, String lastName, String thirdName,
                             Integer serviceNumber, Integer dep, byte[] photoId,
                             String postName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.thirdName = thirdName;
        this.serviceNumber = serviceNumber;
        this.dep = dep;
        this.photoId = photoId;
        this.date = LocalDate.now();
        this.postName = postName;
        this.phone = phone;
    }

}
