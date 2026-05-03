package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.ProfileUpdaterequestDto;
import com.personal.projects.airBnbApp.dto.UserDto;
import com.personal.projects.airBnbApp.entity.User;
import org.jspecify.annotations.Nullable;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdaterequestDto profileUpdateRequestDto);

     UserDto getMyProfile();
}
