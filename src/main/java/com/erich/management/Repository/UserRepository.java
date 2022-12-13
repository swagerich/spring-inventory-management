package com.erich.management.Repository;

import com.erich.management.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    //@Query("select u from User u where u.email= :email")
    Optional<User> findUserByEmail(String email);
}
