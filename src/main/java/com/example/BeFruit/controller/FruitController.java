package com.example.BeFruit.controller;

import com.example.BeFruit.FruitService;
import com.example.BeFruit.model.Fruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @Autowired
    private RestTemplate restTemplate;

    private final String jsonServerUrl = "https://json-fruit.onrender.com/fruits";
    private List<Fruit> fruits = new ArrayList<Fruit>();

    @Autowired
    private FruitService fruitService;

    @GetMapping("/all")
    public List<Fruit> getAllFruits() {
        return fruitService.getAllFruits();
    }

    @GetMapping
    public List<Fruit> getFruits() {
        return fruitService.getFruits();
    }
    @PostMapping
    public Fruit addFruit(@RequestBody Fruit fruit) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Fruit> entity = new HttpEntity<>(fruit, headers);
        return restTemplate.exchange(jsonServerUrl, HttpMethod.POST, entity, Fruit.class).getBody();
    }

    @PutMapping("/{id}")
    public Fruit updateFruit(@PathVariable String id, @RequestBody Fruit updatedFruit) {
        String url = jsonServerUrl + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Fruit> entity = new HttpEntity<>(updatedFruit, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, Fruit.class).getBody();
    }

    @DeleteMapping("/{id}")
    public void deleteFruit(@PathVariable String id) {
        String url = jsonServerUrl + "/" + id;
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }
}
