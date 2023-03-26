package com.example.quiz.Rating.DTO;

public record AddRatingRequest(String username,Long quiz_id,Integer rating) {
}
