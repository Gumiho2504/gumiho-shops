package com.dailycodework.gumiho_shops.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.gumiho_shops.request.LoginRequest;
import com.dailycodework.gumiho_shops.response.ApiResponse;
import com.dailycodework.gumiho_shops.response.JwtRespone;
import com.dailycodework.gumiho_shops.security.jwt.JwtUtils;
import com.dailycodework.gumiho_shops.security.user.GumihoShopUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            GumihoShopUserDetails userDetails = (GumihoShopUserDetails) authentication.getPrincipal();
            JwtRespone jwtRespone = new JwtRespone(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("login success!", jwtRespone));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse("Invalid email or password", e.getMessage()));
        }
    }
}
