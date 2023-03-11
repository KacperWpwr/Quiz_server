package com.example.quiz.Security.Authentication.DTO;

import lombok.Builder;

@Builder
public record LoginChangeResult(String token, String username) {
}
