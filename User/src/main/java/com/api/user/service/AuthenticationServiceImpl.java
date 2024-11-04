package com.api.user.service;

import com.api.user.dto.AuthenticationRequestDTO;
import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.dto.RegisterRequestDTO;
import com.api.user.exeptions.InvalidCredentialsException;
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


    AuthenticationServiceImpl (UserRepository userRepository, JwtServiceImpl jwtServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.jwtServiceImpl = jwtServiceImpl;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        try {
            var user = new User(requestDTO.first_name(),
                    requestDTO.lastname(),
                    requestDTO.email().toLowerCase(),
                    encodePassword(requestDTO.password()),
                    new Role("USER", 1));
            userRepository.save(user);
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getRole().getName());

            var jwtToken = jwtServiceImpl.generateToken(claims, user.getId());
            return new AuthenticationResponseDTO(jwtToken,
                    user.getFirst_name(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole()
            );
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

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
                    user.getRole()
            );
        }
        throw new InvalidCredentialsException("invalid credentials");




    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password + pepper);
    }

}
