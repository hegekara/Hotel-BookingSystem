package com.hotel.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hotel.constants.Role;
import com.hotel.entities.user.BaseUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        System.out.println(request.getHeader("Authorization"));
    
        String token = extractToken(request);

        System.out.println(token);
    
        if (token != null && !jwtUtil.isTokenExpired(token)) {
            try {
                String email = jwtUtil.extractEmail(token);
                Role role = jwtUtil.extractRole(token);
    
                BaseUser user = new BaseUser();
                user.setEmail(email);
                user.setPassword("");
                user.setRole(role);
    
                CustomUserDetails customUserDetails = new CustomUserDetails(user);
    
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
            }
        }
    
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}

