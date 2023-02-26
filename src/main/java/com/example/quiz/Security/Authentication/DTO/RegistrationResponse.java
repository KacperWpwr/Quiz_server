package com.example.quiz.Security.Authentication.DTO;

public record RegistrationResponse(String login, String password, String match_password, String email) {
}
