package com.example.quiz.Security.Configuration.JWT;

import com.example.quiz.User.User;
import com.example.quiz.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String  auth_header = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(auth_header == null ){
            filterChain.doFilter(request,response);
            return;
        }
        if(!auth_header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = auth_header.substring(7);
        username = jwtService.extractUsername(jwt);
        if (username==null || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        User user = this.userService.loadUserByUsername(username);
        if(user==null){
            filterChain.doFilter(request,response);
            return;
        }
        if (!jwtService.validateToken(jwt,user)){

            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        auth_token.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(auth_token);
        filterChain.doFilter(request,response);
    }
}
