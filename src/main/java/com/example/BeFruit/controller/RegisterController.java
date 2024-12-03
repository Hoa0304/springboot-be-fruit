package com.example.BeFruit.controller;

import com.example.BeFruit.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            // URL của JSON Server
            URL jsonServerUrl = new URL("https://json-fruit.onrender.com/users");
            HttpURLConnection jsonConnection = (HttpURLConnection) jsonServerUrl.openConnection();
            jsonConnection.setRequestMethod("POST");
            jsonConnection.setRequestProperty("Content-Type", "application/json");
            jsonConnection.setDoOutput(true);

            // Tạo JSON từ thông tin người dùng
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
                return new ResponseEntity<>("Đăng ký thành công!", HttpStatus.CREATED);
            } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                return new ResponseEntity<>("Email đã tồn tại!", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Đăng ký không thành công!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Xử lý khi xảy ra lỗi kết nối hoặc lỗi khác
            e.printStackTrace();
            return new ResponseEntity<>("Đã xảy ra lỗi trong quá trình đăng ký!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}