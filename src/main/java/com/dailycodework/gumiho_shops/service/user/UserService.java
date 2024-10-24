package com.dailycodework.gumiho_shops.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dailycodework.gumiho_shops.dto.UserDto;
import com.dailycodework.gumiho_shops.exception.AlreadyExistingException;
import com.dailycodework.gumiho_shops.exception.ResourceNotFoundException;
import com.dailycodework.gumiho_shops.model.User;
import com.dailycodework.gumiho_shops.repository.UserRepository;
import com.dailycodework.gumiho_shops.request.user.CreateUserRequest;
import com.dailycodework.gumiho_shops.request.user.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User creatUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(rq -> {
                    User user = new User();
                    user.setEmail(rq.getEmail());
                    user.setPassword(passwordEncoder.encode(rq.getPassword()));
                    user.setFirstName(rq.getFirstName());
                    user.setLastName(rq.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(
                        () -> new AlreadyExistingException(request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(
                () -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("User not found!");
                });
    }

    @Override
    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
