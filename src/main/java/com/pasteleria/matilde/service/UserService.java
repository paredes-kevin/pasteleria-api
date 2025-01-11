package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
