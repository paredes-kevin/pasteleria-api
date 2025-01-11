package com.pasteleria.matilde.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;

    // Getters and Setters
}
