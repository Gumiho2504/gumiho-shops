package com.dailycodework.gumiho_shops.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRespone {
    private Long id;
    private String token;
}
