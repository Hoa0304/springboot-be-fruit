package com.example.BeFruit.controller;

import com.example.BeFruit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {

   @Autowired
   private RestTemplate restTemplate;
   private final String jsonServeUrl = "https://json-fruit.onrender.com";

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Kiểm tra dữ liệu đầu vào
        String url = jsonServeUrl + "/users";
        List<User> users = Arrays.asList(restTemplate.getForObject(url, User[].class));
        System.out.println(users.toString());
        for (User user1 : users) {
            if(user1.getEmail().equals(user.getEmail()) && user1.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Login successfully!", HttpStatus.OK);
            }
        }
            return new ResponseEntity<>("Email or password is incorrect!", HttpStatus.UNAUTHORIZED);
        }
    }