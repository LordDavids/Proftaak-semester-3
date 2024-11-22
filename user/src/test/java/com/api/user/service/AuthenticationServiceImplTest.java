package com.api.user.service;

import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.dto.RegisterRequestDTO;
import com.api.user.entities.User;
import com.api.user.exeptions.UserAlreadyExistsException;
import com.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtServiceImpl;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;


    //good flow test for register method
    @DisplayName("register-good-flow-test")
    @Test
    public void registerGoodFlowTest() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(
                "john",
                "Dou",
                "jhondou@gmail.com",
                "123456",
                "1234567890");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0); // Get the User passed to save
            savedUser.setId(1L); // Set the ID
            return savedUser; // Return the modified User
        });

        doReturn("jwtToken").when(jwtServiceImpl).generateToken(anyMap(), anyLong());
        // Act
        AuthenticationResponseDTO response = authenticationServiceImpl.register(registerRequestDTO);
        // Assert
        assertEquals("jwtToken", response.getToken());
        assertEquals("john", response.getFirstName());
        assertEquals("Dou", response.getLastName());
        assertEquals("jhondou@gmail.com", response.getEmail());
        assertEquals("USER", response.getRole().getName());
    }






    @DisplayName("unittest-register-EmailAlreadyInUse-test")
    @Test
    public void registerEmailAlreadyInUse() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(
                "john",
                "Dou",
                "jhondou@gmail.com",
                "123456",
                "1234567890");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert //
        // Assert that the exception is thrown and the message is correct
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationServiceImpl.register(registerRequestDTO);
        });
        assertEquals("User with this email already exists", exception.getMessage());
    }




}
