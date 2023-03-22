package com.example.quiz.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select case when count(user)>0 then true else false end from User_Account as user where user.email =:email")
    boolean userExistEmail(@Param("email")String email);
    @Query("select case when count(user)>0 then true else false end from User_Account as user where user.login =:login")
    boolean userExistLogin(@Param("login")String login);

    @Query("select user from User_Account as user where user.login=:username")
    User loadUserByUsername(@Param("username")String username);

    @Query("select user from User_Account as user where user.login like %:query%")
    List<User> strictSearchByLogin(@Param("query") String search_query);
}
