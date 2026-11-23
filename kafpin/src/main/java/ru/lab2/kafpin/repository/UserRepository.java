package ru.lab2.kafpin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lab2.kafpin.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
