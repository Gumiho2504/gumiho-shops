package com.dailycodework.gumiho_shops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RestController
@RequestMapping("${api.prefix}/product/")
public class ProductController {

    @GetMapping("")
    public String getMethodName() {
        return "hello";
    }

}
