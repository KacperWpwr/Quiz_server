package com.example.quiz.User;

import com.example.quiz.Quiz.Wrappers.QuizDTOList;
import com.example.quiz.QuizHsitory.DTO.QuizHistoryDTO;
import com.example.quiz.User.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService user_service;
    @GetMapping("/{username}/profile-info")
    public ResponseEntity<UserAccountInfoDTO> getAccountInfo(@PathVariable String username){
        return new ResponseEntity<>(user_service.getAccountInfo(username),HttpStatusCode.valueOf(200));
    }
    @PutMapping("/change/description")
    public ResponseEntity<UserDescriptionDTO> changeDescription(@RequestBody UserDescriptionChangeRequest request){
        return new ResponseEntity<>(user_service.changeDescription(request),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/{username}/quiz/page-number")
    public ResponseEntity<UserQuizPageNumberDTO> getPageNumber(@PathVariable String username){
        return new ResponseEntity<>(user_service.getPageNumber(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("{username}/quiz/page-content/{page_num}")
    public ResponseEntity<UserPageContentDTO> getPageContent(@PathVariable(name = "username") String username
            , @PathVariable(name = "page_num") Integer page_num){
        return new ResponseEntity(user_service.getPageContext(username,page_num),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/{username}/quiz-history")
    public ResponseEntity<QuizHistoryDTO> getUserQuizHistory(@PathVariable String username){
        return new ResponseEntity(new QuizHistoryDTO(user_service.getUserHistory(username)),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/search/strict/{query}")
    public ResponseEntity<List<UserDisplayDTO>> strictSearch(@PathVariable String query,@RequestParam(name ="user" ,defaultValue = "") String username){
        return new ResponseEntity<>(user_service.strictSearch(query,username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/search/advanced/{query}")
    public ResponseEntity<List<UserDisplayDTO>> advancedSearch(@PathVariable String query,@RequestParam(name ="user" ,defaultValue = "") String username){
        return new ResponseEntity<>(user_service.advancedSearch(query,username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/creator/info/{username}")
    public ResponseEntity<CreatorDisplayDTO> getCreatorInfo(@PathVariable String username){
        return new ResponseEntity<>(user_service.getCreatorPage(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/creator/newest-quizzes/{username}")
    public ResponseEntity<UserQuizDTO> getCreatorNewestQuizzes(@PathVariable String username){
        return new ResponseEntity<>(user_service.getNewestQuizzes(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/creator/top-quizzes/{username}")
    public ResponseEntity<UserQuizDTO> getCreatorTopQuizzes(@PathVariable String username){
        return new ResponseEntity<>(user_service.getNewestQuizzes(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/creator/quizzes/{username}")
    public ResponseEntity<UserQuizDTO> getCreatorQuizzes(@PathVariable String username){
        return new ResponseEntity<>(user_service.getUserQuizzes(username),HttpStatusCode.valueOf(200));
    }
    @PostMapping("/follow")
    public ResponseEntity<UserFollowedDTO> followUser(@RequestBody FollowUserRequest request){
        return new ResponseEntity<>(user_service.followCreator(request),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/followed/{username}")
    public ResponseEntity<UserFollowedDTO> getFollowedUsers(@PathVariable String username){
        return new ResponseEntity<>(user_service.getFollwedUsers(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam("following") String following_username,
                                               @RequestParam("followed") String followed_username){
        return new ResponseEntity<>(user_service.isFollowing(followed_username,following_username),
                HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/unfollow")
    public ResponseEntity<UserFollowedDTO> unfollowUser(@RequestBody FollowUserRequest request){
        return new ResponseEntity<>(user_service.unfollowCreator(request),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/{username}/recent-quizzes")
    public ResponseEntity<QuizDTOList> getRecentQuizzes(@PathVariable String username){
        return new ResponseEntity<>(user_service.getRecentQuizzes(username),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/{username}/given-opinion")
    public ResponseEntity<Boolean> hasGivenOpinion(@PathVariable String username, @RequestParam(name = "quiz") Long quiz_id){
        return new ResponseEntity<>(user_service.hasGivenOpinion(username,quiz_id),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/{username}/proposed-quizzes")
    public ResponseEntity<QuizDTOList> getProposedQuizzes(@PathVariable String username){
        return new ResponseEntity<>(user_service.getProposedQuizzes(username),HttpStatusCode.valueOf(200));
    }


}
