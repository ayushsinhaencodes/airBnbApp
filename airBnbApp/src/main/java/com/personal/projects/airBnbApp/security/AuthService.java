package com.personal.projects.airBnbApp.security;

import com.personal.projects.airBnbApp.dto.SignUpRequestDto;
import com.personal.projects.airBnbApp.dto.UserDto;
import com.personal.projects.airBnbApp.entity.User;
import com.personal.projects.airBnbApp.entity.enums.Role;
import com.personal.projects.airBnbApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {

        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);

        if (user != null) {
            throw new RuntimeException("User isalready present with same email id");
        }
        User newUser=modelMapper.map(signUpRequestDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser=userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);

    }
    public String[] login()
}
