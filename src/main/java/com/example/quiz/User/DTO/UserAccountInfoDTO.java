package com.example.quiz.User.DTO;

import lombok.Builder;

@Builder
public record UserAccountInfoDTO(String login,String email,String description) {
}
