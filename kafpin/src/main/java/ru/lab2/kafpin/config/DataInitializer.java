package ru.lab2.kafpin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.lab2.kafpin.model.Role;
import ru.lab2.kafpin.model.User;
import ru.lab2.kafpin.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            // Обновляем или создаём user с паролем pass1
            User user = repo.findByUsername("user").orElse(new User());
            user.setUsername("user");
            user.setPassword(encoder.encode("pass1"));
            user.setRole(Role.ROLE_USER);
            repo.save(user);

            // Обновляем или создаём admin с паролем pass2
            User admin = repo.findByUsername("admin").orElse(new User());
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("pass2"));
            admin.setRole(Role.ROLE_ADMIN);
            repo.save(admin);

            System.out.println("DataInitializer: пароли обновлены");
            System.out.println("user / pass1");
            System.out.println("admin / pass2");
        };
    }
}