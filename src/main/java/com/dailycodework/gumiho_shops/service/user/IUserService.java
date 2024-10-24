package com.dailycodework.gumiho_shops.service.user;

import com.dailycodework.gumiho_shops.dto.UserDto;
import com.dailycodework.gumiho_shops.model.User;
import com.dailycodework.gumiho_shops.request.user.CreateUserRequest;
import com.dailycodework.gumiho_shops.request.user.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);

    User creatUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto toUserDto(User user);

    User getAuthenticatedUser();

}
