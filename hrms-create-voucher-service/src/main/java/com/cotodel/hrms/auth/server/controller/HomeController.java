package com.cotodel.hrms.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
    	System.out.println("homepage");
        return "Welcome to the homepage!";
    }
    
}