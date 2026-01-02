package com.ganesh.User.service.Implement;

import com.ganesh.User.dto.CreateUserRequestDto;
import com.ganesh.User.dto.UpdateUserRequestDto;
import com.ganesh.User.dto.UserDto;
import com.ganesh.User.entity.UserEntity;
import com.ganesh.User.repository.UserRepository;
import com.ganesh.User.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImp(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID" + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(CreateUserRequestDto requestDto) {
        UserEntity newUser = modelMapper.map(requestDto, UserEntity.class);
        UserEntity user = userRepository.save(newUser);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID" + id));

        modelMapper.map(requestDto, user);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updatePartialUser(Long id, UpdateUserRequestDto requestDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID" + id));
        if(requestDto.getName() != null) {
            user.setName(requestDto.getName());
        }
        if(requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }
        if(requestDto.getAddress() != null) {
            user.setAddress(requestDto.getAddress());
        }

        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("User does not exists by ID" +id);
        }
        userRepository.deleteById(id);
    }
}
