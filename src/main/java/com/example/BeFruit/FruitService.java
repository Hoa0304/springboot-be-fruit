package com.example.BeFruit;

import com.example.BeFruit.model.Fruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FruitService {

    private final String FRUIT_API_URL = "http://localhost:3000";

    @Autowired
    private RestTemplate restTemplate;

    public List<Fruit> getAllFruits() {
        ResponseEntity<List<Fruit>> response = restTemplate.exchange(
                FRUIT_API_URL + "/fruitsall",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Fruit>>() {}
        );
        return response.getBody();
    }

    public List<Fruit> getFruits() {
        ResponseEntity<List<Fruit>> response = restTemplate.exchange(
                FRUIT_API_URL+"/fruits",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Fruit>>() {}
        );
        return response.getBody();
    }
}
