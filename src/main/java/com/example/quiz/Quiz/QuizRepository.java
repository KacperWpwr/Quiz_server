package com.example.quiz.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    @Query("select quiz from Quiz as quiz where quiz.quiz_name like %:name%")
    List<Quiz> getQuizByNameStrict(@Param("name") String name);
}
