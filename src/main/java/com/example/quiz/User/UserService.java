package com.example.quiz.User;

import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import com.example.quiz.Quiz.Quiz;
import com.example.quiz.QuizHsitory.DTO.QuizRecordDTO;
import com.example.quiz.QuizHsitory.QuizRecord;
import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.User.DTO.*;
import com.example.quiz.User.Exceptions.PageOutOfBoundsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Getter
    @AllArgsConstructor
    private class QueryMatch{
        private long id;
        private int match;

    }
    private final Integer QUIZZES_PER_PAGE=7;

    private final UserRepository user_repository;
    public boolean emailExists(String email){
        return user_repository.userExistEmail(email);
    }
    public boolean loginExists(String login){
        return user_repository.userExistLogin(login);
    }

    public void reqisterNewUser(User new_user) {

        user_repository.save(new_user);
    }
    public User saveUser(User user){
        return user_repository.save(user);
    }

    @Override
    public User loadUserByUsername(String username)  {
        return user_repository.loadUserByUsername(username);
    }

    public UserAccountInfoDTO getAccountInfo(String username) {
        User user = user_repository.loadUserByUsername(username);
        if(user == null) throw new InvalidLoginException();

        return UserAccountInfoDTO.builder()
                .login(username)
                .email(user.getEmail())
                .description(user.getDescription())
                .build();
    }

    public UserDescriptionDTO changeDescription(UserDescriptionChangeRequest request) {
        User user = user_repository.loadUserByUsername(request.username());
        if(user == null) throw new InvalidLoginException();
        user.setDescription(request.new_description());
        user_repository.save(user);

        return UserDescriptionDTO.builder()
                .description(user.getDescription())
                .build();

    }

    public UserQuizPageNumberDTO getPageNumber(String username) {
        User user =  user_repository.loadUserByUsername(username);
        if(user == null) throw new InvalidLoginException();

        Integer pages_num = (int)Math.ceil( user.getQuizNumber().doubleValue() / QUIZZES_PER_PAGE.doubleValue());

        return new UserQuizPageNumberDTO(pages_num);
    }

    public UserPageContentDTO getPageContext(String username, Integer page_num) {
        User user =  user_repository.loadUserByUsername(username);
        if(user == null) throw new InvalidLoginException();
        Integer pages_num = (int)Math.ceil( user.getQuizNumber().doubleValue() / QUIZZES_PER_PAGE.doubleValue());
        if(page_num>pages_num) throw new PageOutOfBoundsException();
        List<Quiz> page_content= user.getPage(page_num,QUIZZES_PER_PAGE);
        List <QuizInfoDTO> page_info_content = page_content.stream().map(Quiz::createInfoDTO).toList();

        return new UserPageContentDTO(page_info_content);
    }

    public List<QuizRecordDTO> getUserHistory(String username) {
        User user = user_repository.loadUserByUsername(username);
        if(user == null) throw new InvalidLoginException();
        return user.getQuiz_history().stream().map(QuizRecord::getDTO).toList();
    }
    public List<QuizRecordDTO> getRecentQuizzes(String username) {
        User user = user_repository.loadUserByUsername(username);
        if (user == null) throw new InvalidLoginException();
        Set<QuizRecord> recent_quizzes= new HashSet<>(user.getQuiz_history());

        if(recent_quizzes.size()>20){
            return recent_quizzes.stream().map(QuizRecord::getDTO).toList().subList(0,20);
        }else{
            return recent_quizzes.stream().map(QuizRecord::getDTO).toList();
        }
    }

    public List<UserDisplayDTO> strictSearch(String query,String query_username){
        List<User> loaded_users= user_repository.findAll();

        return loaded_users.stream().filter(user -> user.getLogin().contains(query)&&!user.getLogin().equals(query_username))
                .map(User::getDisplayDTO).toList();
    }
    public List<UserDisplayDTO> advancedSearch(String query,String query_username){
        List<User> users= user_repository.findAll();
        List<String> splitted_query = Arrays.stream(query.split("[ ,._-]")).map(String::toLowerCase).toList();
        List<QueryMatch> matches = new ArrayList<>();
        users.forEach(user ->{
            AtomicInteger match_level = new AtomicInteger();
            String username = user.getLogin().toLowerCase();
            splitted_query.forEach(split->{
                if(username.contains(split)){
                    match_level.getAndIncrement();
                }
            });
            if(match_level.get() >0 && !user.getLogin().equals(query_username)){
                matches.add(new QueryMatch(user.getId(),match_level.get()));
            }
        });
        matches.sort((o1, o2) -> Integer.compare(o2.match, o1.match));

        return matches.stream().map(match ->user_repository.findById(match.id).get().getDisplayDTO()).toList();
    }
    public UserQuizDTO getUserQuizzes(String username){
        User user = user_repository.loadUserByUsername(username);
        if(user==null) throw new InvalidLoginException();
        List<QuizInfoDTO> return_list = user.getUser_quizzes().stream()
                .map(Quiz::createInfoDTO).collect(Collectors.toList());
        Collections.reverse(return_list);
        return new UserQuizDTO(return_list);
    }
    public UserQuizDTO getNewestQuizzes(String username){
        User user = user_repository.loadUserByUsername(username);
        if (user==null) throw new InvalidLoginException();

        List<Quiz> user_quizzes = user.getUser_quizzes();
        Collections.reverse(user_quizzes);

        if(user_quizzes.size()<=10){
            return new UserQuizDTO(user_quizzes.stream()
                    .map(Quiz::createInfoDTO).toList());
        }else{
            return new UserQuizDTO(user_quizzes.subList(0,10)
                    .stream().map(Quiz::createInfoDTO).toList());
        }
    }
    public CreatorDisplayDTO getCreatorPage(String username) {
        User user = user_repository.loadUserByUsername(username);

        if(user==null) throw new InvalidLoginException();

        return CreatorDisplayDTO.builder()
                .rating(0.0)
                .quiz_num(user.getQuizNumber())
                .description(user.getDescription())
                .username(user.getUsername())
                .build();
    }
    public UserFollowedDTO followCreator(FollowUserRequest request){
        User user = user_repository.loadUserByUsername(request.following_username());
        if(user==null) throw new InvalidLoginException();
        User followed_user =user_repository.loadUserByUsername(request.followed_username());
        if(followed_user==null) throw new InvalidLoginException();
        user.addFollowedCreator(followed_user);
        followed_user.addFollowingUsers(user);
        user = user_repository.save(user);
        followed_user = user_repository.save(followed_user);

        return new UserFollowedDTO(followed_user.getFollowed_users().stream().map(User::getCreatorInfoDTO).toList());
    }
    public UserFollowedDTO getFollwedUsers(String username) {
        User user = user_repository.loadUserByUsername(username);
        if(user==null) throw new InvalidLoginException();

        return new UserFollowedDTO(user.getFollowed_users().stream().map(User::getCreatorInfoDTO).toList());
    }
    public Boolean isFollowing(String username_followed,String username_following){
        User following = user_repository.loadUserByUsername(username_following);
        if(following==null) throw new InvalidLoginException();
        User followed = user_repository.loadUserByUsername(username_followed);
        if(followed==null) throw new InvalidLoginException();

        return following.getFollowed_users().contains(followed);
    }
    public UserFollowedDTO unfollowCreator(FollowUserRequest request){
        User user = user_repository.loadUserByUsername(request.following_username());
        if(user==null) throw new InvalidLoginException();
        User followed_user =user_repository.loadUserByUsername(request.followed_username());
        if(followed_user==null) throw new InvalidLoginException();
        user.removeFollowedCreator(followed_user);
        followed_user.removeFollowingUsers(user);
        user = user_repository.save(user);
        user_repository.save(followed_user);

        return new UserFollowedDTO(user.getFollowed_users().stream().map(User::getCreatorInfoDTO).toList());
    }

}


