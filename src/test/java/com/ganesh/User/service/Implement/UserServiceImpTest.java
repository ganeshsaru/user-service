package com.ganesh.User.service.Implement;

import com.ganesh.User.dto.CreateUserRequestDto;
import com.ganesh.User.dto.UserDto;
import com.ganesh.User.entity.UserEntity;
import com.ganesh.User.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImp userServiceImp;

    private UserEntity user;
    private UserDto userDto;
    private CreateUserRequestDto requestDto;

    @BeforeEach
    void setup() {
        Long userId = 1L;
        this.user = UserEntity.builder()
                .id(userId)
                .name("Ganesh")
                .email("ganesh@gmail.com")
                .address("Palpa")
                .build();

        this.userDto = UserDto.builder()
                .id(1L)
                .name("Ganesh")
                .email("ganesh@gmail.com")
                .address("Palpa")
                .build();
    }
    @Nested
    @DisplayName("Find user by id testing")
    class FindById {

        @Test
        @DisplayName("Should return user successfully")
        void shouldReturnUserById() {
            //Given (Arrange)
            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));
            when(modelMapper.map(user, UserDto.class))
                    .thenReturn(userDto);

            //when (Act)
            UserDto result = userServiceImp.getUserById(user.getId());

            //then (Assert)
            assertNotNull(result);
            assertEquals(user.getId(), result.getId());

            verify(userRepository, times(1)).findById(user.getId());
            verify(modelMapper, times(1)).map(user, UserDto.class);
        }

        @Test
        @DisplayName("Should Throw IllegalArgumentException when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Given
            Long userId = 2L;

            when(userRepository.findById(userId))
                    .thenReturn(Optional.empty());

            // when (Act)
            /*
                asserThrows takes two parameters
                - Exception Class
                - lambda expression i.e () -> {code}
            */
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userServiceImp.getUserById(userId)
            );

            // Then
            assertEquals("User not found with ID" + userId, exception.getMessage());

            verify(userRepository, times(1)).findById(userId);
            verifyNoInteractions(modelMapper);
        }
    }

    @Nested
    @DisplayName("Create New User Testing")
    class CreateUser {

        @Test
        @DisplayName("Should Create new User")
        void shouldCreateNewUser() {
            CreateUserRequestDto requestDto1 = CreateUserRequestDto.builder()
                    .name("Himal")
                            .email("hims@gmail.com")
                                    .address("Baglung")
                                            .build();
            UserEntity user1 = UserEntity.builder()
                    .id(2L)
                    .name("Himal")
                            .email("hims@gmail.com")
                                    .address("Baglung")
                                            .build();
            UserDto userDto1 = UserDto.builder()
                    .id(2L)
                    .name("Himal")
                            .email("hims@gmail.com")
                                    .address("Baglung")
                                            .build();

            //Given
            when(modelMapper.map(requestDto1, UserEntity.class)).thenReturn(user1);
            when(userRepository.save(user1)).thenReturn(user1);
            when(modelMapper.map(user1,UserDto.class)).thenReturn(userDto1);

            //When
            UserDto savedUser = userServiceImp.createUser(requestDto1);

            //Then
            assertNotNull(savedUser);
            assertEquals(2L,
                    savedUser.getId());
            assertEquals("Himal", savedUser.getName());
            assertEquals("hims@gmail.com", savedUser.getEmail());
            assertEquals("Baglung", savedUser.getAddress());

            verify(userRepository, times(1)).save(user1);
            verify(modelMapper, times(1)).map(requestDto1, UserEntity.class);
            verify(modelMapper,times(1)).map(user1, UserDto.class);
        }
    }

}