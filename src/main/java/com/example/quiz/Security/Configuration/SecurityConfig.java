package com.example.quiz.Security.Configuration;

import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.Security.Authentication.Exceptions.InvalidPasswordException;
import com.example.quiz.Security.Configuration.JWT.JwtAuthenticationFilter;
import com.example.quiz.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    private final UserService user_service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/api/user/auth/**","/api/user/search/**",
                                "/api/quiz/get/**","/api/quiz/search/**",
                                "/api/user/get/creator/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(getAuthenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider auth_provider = new DaoAuthenticationProvider();
        auth_provider.setUserDetailsService(user_service);
        auth_provider.setPasswordEncoder(getPasswordEncoder());
        return auth_provider;

    }
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception{
        //return config.getAuthenticationManager();
        return new AuthenticationManager() {


            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                UserDetails user = user_service.loadUserByUsername(username);
                if(user==null){
                    throw new InvalidLoginException();
                }
                if(!getPasswordEncoder().matches(password, user.getPassword())){
                    throw new InvalidPasswordException();
                }

                Collection<GrantedAuthority> authorities = user.getAuthorities().stream()
                        .map(auth->new SimpleGrantedAuthority(auth.getAuthority()))
                        .collect(Collectors.toList());
                return new UsernamePasswordAuthenticationToken(username,null,authorities);
            }
        };
    }
    @Bean(name = "password_encoder")
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
            }
        };
    }}
