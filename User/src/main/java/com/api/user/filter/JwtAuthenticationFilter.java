package com.api.user.filter;

import com.api.user.jwt.JwtServiceImpl;
import com.api.user.user.UserRepository;
import com.api.user.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtServiceImpl;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(final JwtServiceImpl jwtServiceImpl, UserRepository userRepository) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            final String token;
            final String email;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            token = authHeader.substring(7);
            email = jwtServiceImpl.getEmailFromToken(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<User> optionalUser = userRepository.findByEmail(email);

                if (optionalUser.isPresent() && jwtServiceImpl.validateToken(token)) {

                    User user = optionalUser.get();
                    System.out.println(user.getAuthorities());
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
    }
}
