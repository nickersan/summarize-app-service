package com.tn.summarize.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class CorsConfiguration
{
  @Bean
  WebMvcConfigurer corsConfigurer()
  {
    return new WebMvcConfigurer()
    {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry)
      {
        registry
          .addMapping("/**")
          .allowedMethods("*")
          .allowedOrigins("*")
          .allowedHeaders("*");
      }
    };
  }
}
