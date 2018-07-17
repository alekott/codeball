package com.example.codeball.repositories;

import com.example.codeball.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
