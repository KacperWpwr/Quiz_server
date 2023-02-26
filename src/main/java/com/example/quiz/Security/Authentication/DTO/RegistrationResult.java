package com.example.quiz.Security.Authentication.DTO;

import java.util.Optional;

public record RegistrationResult(boolean is_succesful, Optional<String> token) {
}
