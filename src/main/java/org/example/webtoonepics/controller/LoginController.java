package org.example.webtoonepics.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    // This method will return the login page
    public String login() {
        return "login";
    }
}
