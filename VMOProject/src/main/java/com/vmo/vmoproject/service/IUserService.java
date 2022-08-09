package com.vmo.vmoproject.service;

import com.vmo.vmoproject.entities.User;

public interface IUserService {
    Boolean existsByUsername(String username);//user name da co trong database chua

    Boolean existsByEmail(String email);
    User saveUser(User user);
}
