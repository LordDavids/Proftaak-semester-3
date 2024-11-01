package com.api.user.service;

import com.api.user.dto.AuthenticationRequestDTO;
import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.dto.RegisterRequestDTO;
import com.api.user.exeptions.invalidCredentialsException;
import com.api.user.exeptions.userAlreadyExistsException;
import com.api.user.entities.Role;
import com.api.user.repository.UserRepository;
import com.api.user.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.pepper}")
    private String pepper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;


    AuthenticationServiceImpl( UserRepository userRepository, JwtServiceImpl jwtServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.jwtServiceImpl = jwtServiceImpl;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        try {
            var user = new User(requestDTO.first_name(),
                    requestDTO.lastname(),
                    requestDTO.email().toLowerCase(),
                    EncodePassword(requestDTO.password()),
                    new Role("USER", 1));
            userRepository.save(user);
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getRole().getName());

            var jwtToken = jwtServiceImpl.GenerateToken(claims, user.getId());
            return new AuthenticationResponseDTO(jwtToken,
                    user.getFirst_name(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole()
            );
        } catch (DataIntegrityViolationException ex) {
            throw new userAlreadyExistsException("User with this email already exists");
        }

    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {

        var user = userRepository.findByEmail(requestDTO.email().toLowerCase())
                .orElseThrow(() -> new invalidCredentialsException("invalid credentials"));
        if (passwordEncoder.matches((requestDTO.password() + pepper), user.getPassword())) {
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getRole().getName());

            var jwtToken = jwtServiceImpl.GenerateToken(claims, user.getId());
            return new AuthenticationResponseDTO(jwtToken,
                    user.getFirst_name(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole()
            );
        }
        throw new invalidCredentialsException("invalid credentials");




    }
    private String EncodePassword(String password) {
        return passwordEncoder.encode(password + pepper);
    }

}
