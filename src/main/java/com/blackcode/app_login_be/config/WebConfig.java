package com.blackcode.app_login_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Izinkan CORS untuk semua endpoint
                .allowedOrigins("http://localhost:5173") // Ganti dengan frontend Anda
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Metode HTTP yang diizinkan
                .allowedHeaders("Authorization", "Content-Type") // Header yang diizinkan
                .allowCredentials(true); // Jika perlu mengirimkan cookies/credentials
    }
}
