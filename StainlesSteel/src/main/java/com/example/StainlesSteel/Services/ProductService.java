package com.example.StainlesSteel.Services;

import com.example.StainlesSteel.Repository.ProductImageRepository;
import com.example.StainlesSteel.Repository.ProductRepository;
import com.example.StainlesSteel.Repository.ProductSpecificationRepository;
import com.example.StainlesSteel.Tables.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ProductSpecificationRepository specRepository;
    private final FileStorageService fileStorageService;

    public ProductResponse createProduct(ProductDTO productDTO, MultipartFile[] imageFiles) {
        // Build and save product
        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .size(productDTO.getSize())
                .color(productDTO.getColor())
                .description(productDTO.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        // Process images
        List<String> imageUrls = Arrays.stream(imageFiles)
                .map(fileStorageService::storeFile)
                .peek(url -> {
                    ProductImage image = ProductImage.builder()
                            .imageUrl(url)
                            .product(savedProduct)
                            .build();
                    savedProduct.addImage(image);
                })
                .collect(Collectors.toList());

        // Process specifications
        productDTO.getSpecifications().forEach(specText -> {
            ProductSpecification spec = ProductSpecification.builder()
                    .specText(specText)
                    .product(savedProduct)
                    .build();
            savedProduct.addSpecification(spec);
        });

        productRepository.save(savedProduct);

        // Build response
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .imageUrls(imageUrls)
                .price(savedProduct.getPrice())
                .size(savedProduct.getSize())
                .color(savedProduct.getColor())
                .description(savedProduct.getDescription())
                .specifications(productDTO.getSpecifications())
                .build();
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setColor(product.getColor());
            response.setSize(product.getSize());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());


            List<String> imageUrls = product.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
            response.setImageUrls(imageUrls);


            List<String> specs = product.getSpecifications().stream()
                    .map(ProductSpecification::getSpecText)
                    .collect(Collectors.toList());
            response.setSpecifications(specs);

            return response;
        }).collect(Collectors.toList());
    }
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found with id: " + id
                ));

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setColor(product.getColor());
        response.setSize(product.getSize());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());

        List<String> imageUrls = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
        response.setImageUrls(imageUrls);

        List<String> specs = product.getSpecifications().stream()
                .map(ProductSpecification::getSpecText)
                .collect(Collectors.toList());
        response.setSpecifications(specs);

        return response;
    }


}
