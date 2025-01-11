package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUserId(Long userId);
    Optional<PasswordResetToken> findByToken(String token);
}
