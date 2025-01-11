package com.pasteleria.matilde.response;

import com.pasteleria.matilde.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String messages;

    private USER_ROLE role;
}
