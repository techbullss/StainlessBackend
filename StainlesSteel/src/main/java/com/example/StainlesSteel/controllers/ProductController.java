package com.example.StainlesSteel.controllers;

import com.example.StainlesSteel.Services.ProductService;
import com.example.StainlesSteel.Tables.ProductDTO;
import com.example.StainlesSteel.Tables.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://stainless-frontend.vercel.app")
@RequestMapping("/api/products")

public class ProductController {
    @Autowired
     ProductService productService;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @RequestParam("product") String productJson,
            @RequestParam("images") MultipartFile[] images) {
System.out.println(Arrays.toString(images));
        try {
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
            ProductResponse response = productService.createProduct(productDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            e.printStackTrace(); // Or use logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

}