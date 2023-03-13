package com.example.quiz.User;

import com.example.quiz.Quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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
    //TODO: private VerificationToken verification_token
    //TODO: private List<User> followed_users
    //TODO: private List<QuizRecord> quiz_history
    //TODO: private List<SearchQuery> search_history
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


}
