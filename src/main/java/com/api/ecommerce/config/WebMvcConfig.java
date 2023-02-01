package com.api.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path userPhotoDir = Paths.get("./user-photos");
        String userPhotoPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/user-photos/**")
                .addResourceLocations("file:/" + userPhotoPath + "/");
    }
}
