package com.personal.projects.airBnbApp.dto;

import com.personal.projects.airBnbApp.entity.User;
import com.personal.projects.airBnbApp.entity.enums.Gender;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class GuestDto {
    Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
