package com.dailycodework.gumiho_shops.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<OrderDto> orders;
    private CartDto cart;
}
