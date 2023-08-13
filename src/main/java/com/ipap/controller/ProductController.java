package com.ipap.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Integer, String> products = new HashMap<>();

    @PostConstruct
    private void addItemsToHashMap() {
        products.put(1, "Playstation 5");
        products.put(2, "iPhone 15");
        products.put(3, "Headset");
        products.put(4, "Gaming Controller");
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllProducts() {
        return ResponseEntity.ok(products.values().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAllProducts(@PathVariable Integer id) {
        String response = Optional.ofNullable(products.get(id)).orElse("Product not found");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProduct(@PathVariable Integer id, @RequestBody String product) {
        return ResponseEntity.ok(products.putIfAbsent(id, product));
    }
}
