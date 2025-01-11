package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.PasswordResetToken;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.repository.PasswordResetTokenRepository;
import com.pasteleria.matilde.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createPasswordResetTokenForUser(User user) {
        try {
            PasswordResetToken existingToken = tokenRepository.findByUserId(user.getId());

            if (existingToken != null) {
                existingToken.setToken(UUID.randomUUID().toString());
                existingToken.setExpiryDate(LocalDateTime.now().plusHours(1));
                tokenRepository.save(existingToken);
                return existingToken.getToken();
            } else {
                PasswordResetToken token = new PasswordResetToken();
                token.setToken(UUID.randomUUID().toString());
                token.setUser(user);
                token.setExpiryDate(LocalDateTime.now().plusHours(1));
                tokenRepository.save(token);
                return token.getToken();
            }
        } catch (Exception e) {
            // Manejar la excepción adecuadamente
            throw new RuntimeException("Error al crear el token de restablecimiento de contraseña", e);
        }
    }


    public User validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        return resetToken.getUser();
    }



    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}