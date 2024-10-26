package com.dailycodework.gumiho_shops.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dailycodework.gumiho_shops.model.User;
import com.dailycodework.gumiho_shops.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GumihoShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return GumihoShopUserDetails.buildUserDetails(user);
    }

}