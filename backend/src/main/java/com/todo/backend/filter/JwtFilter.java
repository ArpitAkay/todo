package com.todo.backend.filter;

import com.todo.backend.dto.UserInfoUserDetailsService;
import com.todo.backend.entity.Role;
import com.todo.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        if(authorizationHeader != null  && authorizationHeader.startsWith("Bearer")) {
            token = authorizationHeader.substring(7);

            try {
                email = jwtUtil.extractUsername(token);
            }
            catch(IllegalArgumentException e) {
                System.out.println("Unable to get Jwt token");
            }
            catch(ExpiredJwtException ex) {
                System.out.println("Jwt token has expired");
            }
            catch(MalformedJwtException exx) {
                System.out.println("Invalid Jwt");
            }

        }
        else{
            System.out.println("Enter a valid JWT token");
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else{
                System.out.println("Invalid JWT token");
            }
        }
        else{
            System.out.println("username is null or context is not null");
        }
        filterChain.doFilter(request, response);
    }
}
