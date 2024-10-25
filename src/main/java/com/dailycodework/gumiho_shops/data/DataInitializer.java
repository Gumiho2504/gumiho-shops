package com.dailycodework.gumiho_shops.data;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dailycodework.gumiho_shops.model.Role;
import com.dailycodework.gumiho_shops.model.User;
import com.dailycodework.gumiho_shops.repository.RoleRepository;
import com.dailycodework.gumiho_shops.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.*;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Set<String> defaulRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExits(defaulRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(email)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Gumiho");
            user.setLastName("User-" + i);
            user.setEmail(email);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("user - " + i + "create user success!");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String email = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(email)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Gumiho");
            user.setLastName("Admin-" + i);
            user.setEmail(email);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("admin - " + i + "create user success!");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> rolses) {
        rolses.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);

    }

}
