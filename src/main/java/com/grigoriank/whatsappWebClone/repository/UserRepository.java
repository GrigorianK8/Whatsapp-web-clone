package com.grigoriank.whatsappWebClone.repository;

import com.grigoriank.whatsappWebClone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

     User findByEmail(String email);

     @Query("select u from User u where u.fullName like %:query% or u.email like %:query%")
     List<User> searchUser(@Param("query") String query);
}
