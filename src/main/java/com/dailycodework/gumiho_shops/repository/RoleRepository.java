package com.dailycodework.gumiho_shops.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.gumiho_shops.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);

}
