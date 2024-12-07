package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.model.User;
import com.sdp.FeedBackManagement.repository.UserRepository;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Fail";
    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public List<User> getUser() {
        return repo.findAll();
    }

    public String getUserRole(User user) {
        User foundUser = repo.findByUsername(user.getUsername());
        return foundUser != null ? foundUser.getRole() : null;
    }

    public User getUserByUsername(String username) {
        User user = repo.findByUsername(username);
        if(user!=null){
            user.setPassword(null);
        }
        return user;
    }

}
