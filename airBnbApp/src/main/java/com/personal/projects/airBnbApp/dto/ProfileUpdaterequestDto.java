package com.personal.projects.airBnbApp.dto;

import com.personal.projects.airBnbApp.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdaterequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
