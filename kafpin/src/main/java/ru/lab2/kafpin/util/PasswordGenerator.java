package ru.lab2.kafpin.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String pass1 = "pass1";
        String pass2 = "pass2";
        System.out.printf("Пароль для %s - %s%n", pass1, encoder.encode(pass1));
        System.out.printf("Пароль для %s - %s%n", pass2, encoder.encode(pass2));
    }
}
