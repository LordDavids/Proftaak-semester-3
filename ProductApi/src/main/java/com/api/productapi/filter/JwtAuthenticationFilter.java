package com.api.productapi.filter;

import com.api.productapi.entities.JwtUser;
import com.api.productapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtServiceImpl;

    public JwtAuthenticationFilter(final JwtService jwtServiceImpl) {
        this.jwtServiceImpl = jwtServiceImpl;

    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getJwtFromCookies(request);
        final Long id;

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtServiceImpl.validateToken(token)) {
                JwtUser user = jwtServiceImpl.getUserFromToken(token);
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

    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // Get the cookies
        if (cookies == null) {
            return null; // Return null if no cookies are present
        }

        for (Cookie cookie : cookies) { // Iterate over the cookies
            if (cookie.getName().equals("JWT")) {
                return cookie.getValue(); // Return the JWT if found
            }
        }

        return null; // Return null if the JWT cookie is not found
    }
}
