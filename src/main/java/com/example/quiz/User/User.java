package com.example.quiz.User;

import com.example.quiz.Quiz.Quiz;
import com.example.quiz.QuizHsitory.QuizRecord;
import com.example.quiz.Rating.Rating;
import com.example.quiz.User.DTO.CreatorDisplayDTO;
import com.example.quiz.User.DTO.CreatorInfoDTO;
import com.example.quiz.User.DTO.UserDisplayDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "User_Account")
@Table
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    private String login;
    private String password;
    private String email;
    @Builder.Default
    private String description="";
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean is_enabled;
    private Boolean is_blocked;
    @OneToMany(
            mappedBy = "creator"
    )
    private List<Quiz> user_quizzes;
    @OneToMany(mappedBy = "user")
    private List<QuizRecord> quiz_history;
    @ManyToMany
    @JoinTable(
            name="following_table",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"followed_id","following_id"})

    )
    private List<User> followed_users;
    @ManyToMany(mappedBy ="followed_users", fetch = FetchType.EAGER)
    private List<User> following_users;

    @OneToMany(mappedBy = "user")
    private Set<Rating> user_ratings;
    //TODO: private VerificationToken verification_token
    private Double creator_rating =0.0;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    public void addQuiz(Quiz quiz){
        user_quizzes.add(quiz);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !is_blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return is_enabled;
    }

    public Integer getQuizNumber() {
        return user_quizzes.size();
    }

    public List<Quiz> getPage(Integer page_num, Integer quizzes_per_page) {
        int begin = page_num*quizzes_per_page- quizzes_per_page;
        int end = Math.min(page_num * quizzes_per_page, user_quizzes.size());
        return user_quizzes.subList(begin,end);
    }
    public void addQuizRecord(QuizRecord record){
        if(quiz_history.size()==100){
            quiz_history.remove(0);
        }
        quiz_history.add(record);
    }
    public UserDisplayDTO getDisplayDTO(){
        return new UserDisplayDTO(login);
    }
    public CreatorDisplayDTO getCreatorDisplayDTO(){
        return CreatorDisplayDTO.builder()
                .username(login)
                .description(description)
                .quiz_num(user_quizzes.size())
                .rating(creator_rating)
                .build();
    }
    public CreatorInfoDTO getCreatorInfoDTO(){
        return CreatorInfoDTO.builder()
                .username(login)
                .quiz_num(user_quizzes.size())
                .rating(creator_rating)
                .build();
    }
    public List<UserDisplayDTO> addFollowedCreator(User user){
        if(!followed_users.contains(user)){
            followed_users.add(user);
        }
        return followed_users.stream().map(User::getDisplayDTO).toList();
    }
    public List<UserDisplayDTO> addFollowingUsers(User user){
        if(!following_users.contains(user)){
            following_users.add(user);
        }
        return following_users.stream().map(User::getDisplayDTO).toList();
    }
    public List<UserDisplayDTO> removeFollowedCreator(User user){
        followed_users = followed_users.stream().filter(user1 -> user1.id != user.id).collect(Collectors.toList());
        return followed_users.stream().map(User::getDisplayDTO).toList();
    }
    public List<UserDisplayDTO> removeFollowingUsers(User user){
        following_users = following_users.stream().filter(user1 -> user1.id != user.id).collect(Collectors.toList());
        return following_users.stream().map(User::getDisplayDTO).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addRating(Rating rating){
        user_ratings.add(rating);
    }
    public void calcRating(){
        Double sum =0.0;
        int valid_ratings_number =0;
        for(Quiz quiz : user_quizzes){
            if(quiz.getOverall_rating()!=0.0){
                sum+=quiz.getOverall_rating();
                valid_ratings_number++;
            }
        }
        if(valid_ratings_number != 0){
            creator_rating = sum/valid_ratings_number;
        }else{
            creator_rating= 0.0;
        }
    }
}
