package com.example.quiz.User.DTO;

import lombok.Builder;

@Builder
public record CreatorInfoDTO(String username, Integer quiz_num, Double rating) {
}
