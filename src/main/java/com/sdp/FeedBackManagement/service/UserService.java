package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.exception.UserNotFoundException;
import com.sdp.FeedBackManagement.model.User;
import com.sdp.FeedBackManagement.repository.UserRepository;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JavaMailSender mailSender;

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


    public void sendPasswordResetEmail(String mail) {
        User user = repo.findByMail(mail);
        if (user == null) {
            throw new UserNotFoundException("User with mail " + mail + " not found"); // Throw an exception
        }

        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24)); // Token expires in 24 hours
        repo.save(user);

        // Send email with reset link
        String resetLink = "http://localhost:5173/reset-password?token=" + resetToken;
        // Use your preferred email service to send the email
        // Example using JavaMailSender:
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Password Reset Link");
        message.setText("Click the following link to reset your password: " + resetLink);
        mailSender.send(message);
    }

    private String generateResetToken() {
        // Generate a random token using a secure random number generator
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public User getUserByResetToken(String token) {
        return repo.findByResetToken(token);
    }

    public void resetPassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        repo.save(user);
    }
}
