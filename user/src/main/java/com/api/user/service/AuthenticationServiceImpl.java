package com.api.user.service;

import com.api.user.dto.AuthenticationRequestDTO;
import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.dto.RegisterRequestDTO;
import com.api.user.exeptions.InvalidCredentialsException;
import com.api.user.exeptions.MissingFieldException;
import com.api.user.exeptions.UserAlreadyExistsException;
import com.api.user.entities.Role;
import com.api.user.repository.UserRepository;
import com.api.user.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.pepper}")
    private String pepper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;


    AuthenticationServiceImpl(UserRepository userRepository, JwtServiceImpl jwtServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.jwtServiceImpl = jwtServiceImpl;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
            if (requestDTO.first_name() == null || requestDTO.lastname() == null || requestDTO.email().isEmpty() || requestDTO.phone_number() == null) {
                throw new MissingFieldException("Please check if you filled in all required fields");
            }

            if (userRepository.existsByEmail(requestDTO.email().toLowerCase())) {
                throw new UserAlreadyExistsException("User with this email already exists");
            }
            var user = new User(requestDTO.first_name(),
                    requestDTO.lastname(),
                    requestDTO.email().toLowerCase(),
                    encodePassword(requestDTO.password()),
                    new Role("USER", 1)
                    , requestDTO.phone_number());
            userRepository.save(user);
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getRole().getName());

            var jwtToken = jwtServiceImpl.generateToken(claims, user.getId());
            return new AuthenticationResponseDTO(jwtToken,
                    user.getFirst_name(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole(),
                    user.getPhoneNumber()
            );
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {

        var user = userRepository.findByEmail(requestDTO.email().toLowerCase())
                .orElseThrow(() -> new InvalidCredentialsException("invalid credentials"));
        if (passwordEncoder.matches((requestDTO.password() + pepper), user.getPassword())) {
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getRole().getName());

            var jwtToken = jwtServiceImpl.generateToken(claims, user.getId());
            return new AuthenticationResponseDTO(jwtToken,
                    user.getFirst_name(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole(),
                    user.getPhoneNumber()
            );
        }
        throw new InvalidCredentialsException("invalid credentials");




    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password + pepper);
    }

}
