package com.example.quiz.Security.Authentication.DTO;

public record RegistrationRequest(String login, String password, String match_password, String email) {
}
