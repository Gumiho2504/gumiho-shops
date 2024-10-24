package com.dailycodework.gumiho_shops.data;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dailycodework.gumiho_shops.model.User;
import com.dailycodework.gumiho_shops.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(email)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Gumiho");
            user.setLastName("-" + i);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("user - " + i + "create user success!");
        }
    }

}
