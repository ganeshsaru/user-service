package com.ganesh.User.service;

import com.ganesh.User.dto.CreateUserRequestDto;
import com.ganesh.User.dto.UpdateUserRequestDto;
import com.ganesh.User.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(CreateUserRequestDto requestDto);

    UserDto updateUser(Long id, UpdateUserRequestDto requestDto);

    UserDto updatePartialUser(Long id, UpdateUserRequestDto requestDto);

    void deleteUser(Long id);
}
