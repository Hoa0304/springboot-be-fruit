package com.example.BeFruit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Cấu hình RestTemplate bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // Trả về một instance của RestTemplate
    }
}
