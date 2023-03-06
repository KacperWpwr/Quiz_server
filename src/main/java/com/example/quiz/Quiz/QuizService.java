package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.CreateQuizRequest;
import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.DTO.QuizDisplayDTO;
import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import com.example.quiz.Quiz.Question.Question;
import com.example.quiz.Quiz.Question.QuestionService;
import com.example.quiz.User.User;
import com.example.quiz.User.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    @Getter
    @AllArgsConstructor
    private class QueryMatch{
        private long id;
        private int match;

    }
    private final QuizRepository quiz_repository;
    private final QuestionService question_service;
    private final UserService user_service;

    public  List<QuizInfoDTO> strictNameSearch(String name) {
        List<Quiz> quizzes = quiz_repository.getQuizByNameStrict(name);
        if(quizzes==null){
            return null;
        }
        List<QuizInfoDTO> return_list= new ArrayList<>();

        quizzes.forEach(quiz->{
            return_list.add(quiz.createInfoDTO());
        });
        if(return_list.size()<=20){
            return return_list;
        }else{
            return return_list.subList(0,19);
        }

    }

    public QuizDisplayDTO createQuiz(CreateQuizRequest request){
        Quiz quiz = Quiz.builder()
                .quiz_name(request.quiz_name())
                .questions(new ArrayList<>())
                .creator(null)
                .build();
        quiz = quiz_repository.save(quiz);


        User creator = user_service.loadUserByUsername(request.username());
        if(creator==null) throw new RuntimeException();
        quiz.setCreator(creator);
        quiz = quiz_repository.save(quiz);
        creator.addQuiz(quiz);


        ArrayList<Question> questions = new ArrayList<>();

        for(QuestionDTO question: request.questions()){
            Question new_question = question_service.createQuestion(question, quiz);
            questions.add(new_question);
        }
        quiz.setQuestions(questions);
        return quiz_repository.save(quiz).createDisplayDTO();

    }
    public QuizDisplayDTO getQuizById(Long id){
        return quiz_repository.findById(id).orElseThrow().createDisplayDTO();
    }
    public List<QuizInfoDTO> advancedSearch(String query){
        List<Quiz> quizzes= quiz_repository.findAll();
        String[] sub_queries=query.toLowerCase().split(" \\,\\.");
        List<QueryMatch> matches = new ArrayList<>();
        quizzes.forEach(quiz->{
            int match_level=0;
            String name = quiz.getQuiz_name().toLowerCase();
            for (int i = 0; i < sub_queries.length; i++) {
                if(name.contains(sub_queries[i])){
                    match_level++;
                }
            }
            if(match_level>0){
                matches.add(new QueryMatch(quiz.getId(),match_level));
            }
        });

        return matches.stream().map(match->{
            return quiz_repository.findById(match.id).get().createInfoDTO();
        }).collect(Collectors.toList());
    }
}
