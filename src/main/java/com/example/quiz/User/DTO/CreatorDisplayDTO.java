package com.example.quiz.User.DTO;

import lombok.Builder;

@Builder
public record CreatorDisplayDTO(String username, String description, Integer quiz_num, Double rating) {
}
