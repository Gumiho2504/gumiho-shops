package com.dailycodework.gumiho_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailycodework.gumiho_shops.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
