package com.example.BeFruit.controller;

import com.example.BeFruit.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final PasswordEncoder passwordEncoder;

    public RegisterController() {
        // Khởi tạo PasswordEncoder (BCrypt)
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            // Mã hóa mật khẩu người dùng
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // URL của JSON Server
            URL jsonServerUrl = new URL("https://json-fruit.onrender.com/users");
            HttpURLConnection jsonConnection = (HttpURLConnection) jsonServerUrl.openConnection();
            jsonConnection.setRequestMethod("POST");
            jsonConnection.setRequestProperty("Content-Type", "application/json");
            jsonConnection.setDoOutput(true);

            // Tạo JSON từ thông tin người dùng (mật khẩu đã mã hóa)
            String jsonInput = String.format(
                    "{\"name\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                    user.getName(), user.getEmail(), user.getPassword()
            );

            // Gửi dữ liệu đến JSON Server
            try (OutputStream os = jsonConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi từ JSON Server
            int responseCode = jsonConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
            } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                return new ResponseEntity<>("Email already exists!", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Registration failed!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred during registration!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
