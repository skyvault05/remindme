//package com.skyvault05.remindme.config.web;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//    @Value("${frontEndUrl}")
//    private String frontEndUrl;
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(frontEndUrl)
//                .allowedMethods("GET", "POST", "DELETE")
//                .allowedHeaders("Origin", "Accept", "X-Requested-With", "Content-Type",
//                        "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization")
//                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .allowCredentials(true);
//    }
//}
