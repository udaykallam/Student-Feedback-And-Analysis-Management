package com.sdp.FeedBackManagement.repository;

import com.sdp.FeedBackManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByMail(String mail);
    User findByResetToken(String token);
}
