package com.example.quiz.Rating.DTO;

import lombok.Builder;

@Builder
public record RatingDisplayDTO(String username,Integer rating, String quiz_name) {
}
