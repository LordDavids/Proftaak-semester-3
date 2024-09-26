package com.api.user.authentication;

import com.api.user.user.Role;
import com.api.user.user.UserRepository;
import com.api.user.jwt.JwtServiceImpl;
import com.api.user.user.user;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtServiceImpl jwtServiceImpl, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceImpl = jwtServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        var user = new user(requestDTO.first_name(), requestDTO.lastname(), requestDTO.email(), passwordEncoder.encode(requestDTO.password()), Role.USER);
        userRepository.save(user);

        var claims = new HashMap<String, Object>();
        claims.put("Role", Role.USER);

        var jwtToken = jwtServiceImpl.GenerateToken(claims, requestDTO.email());
        return new AuthenticationResponseDTO(jwtToken);
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                       requestDTO.email(),
                       requestDTO.password()
                )
        );
        var user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow();

        var claims = new HashMap<String, Object>();
        claims.put("Role", Role.USER);

        var jwtToken = jwtServiceImpl.GenerateToken(claims, requestDTO.email());
        return new AuthenticationResponseDTO(jwtToken);
    }
}
