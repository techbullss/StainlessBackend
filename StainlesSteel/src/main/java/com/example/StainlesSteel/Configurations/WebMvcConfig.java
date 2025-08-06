package com.example.StainlesSteel.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Change the path here if your uploads are saved elsewhere
        registry.addResourceHandler("https://stainlessbackend-5.onrender.com/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}

