package com.api.user.authentication;

import com.api.user.exeptions.invalidCredentialsException;
import com.api.user.exeptions.userAlreadyExistsException;
import com.api.user.user.Role;
import com.api.user.user.UserRepository;
import com.api.user.jwt.JwtServiceImpl;
import com.api.user.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.pepper}")
    private String pepper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;


    AuthenticationServiceImpl( UserRepository userRepository, JwtServiceImpl jwtServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);;
        this.jwtServiceImpl = jwtServiceImpl;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        try {
            var user = new User(requestDTO.first_name(),
                    requestDTO.lastname(),
                    requestDTO.email().toLowerCase(),
                    passwordEncoder.encode(requestDTO.password() + pepper),
                    new Role("USER", 1));
            userRepository.save(user);

            var claims = new HashMap<String, Object>();
            claims.put("Role", user.getAuthorities());

            var jwtToken = jwtServiceImpl.GenerateToken(claims, requestDTO.email());
            return new AuthenticationResponseDTO(jwtToken);
        } catch (DataIntegrityViolationException ex) {
            throw new userAlreadyExistsException("User with this email already exists");
        }

    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {

        var user = userRepository.findByEmail(requestDTO.email().toLowerCase())
                .orElseThrow(() -> new invalidCredentialsException("invalid credentials"));
        if (passwordEncoder.matches((requestDTO.password() + pepper), user.getPassword())) {
            var claims = new HashMap<String, Object>();
            claims.put("roles", user.getAuthorities());

            var jwtToken = jwtServiceImpl.GenerateToken(claims, requestDTO.email());
            return new AuthenticationResponseDTO(jwtToken);
        }
        throw new invalidCredentialsException("invalid credentials");




    }
}
