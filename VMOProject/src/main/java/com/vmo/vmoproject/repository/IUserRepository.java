package com.vmo.vmoproject.repository;

import com.vmo.vmoproject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String username);

    Boolean existsByUsername(String username);//user name da co trong database chua

    Boolean existsByEmail(String email);
}
