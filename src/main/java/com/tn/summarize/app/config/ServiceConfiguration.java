package com.tn.summarize.app.config;

import java.security.PublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tn.summarize.app.client.AuthClient;
import com.tn.summarize.app.client.UserClient;
import com.tn.summarize.app.service.UserService;

@Configuration
class ServiceConfiguration
{
  @Bean
  UserService userService(PublicKey publicKey, AuthClient authClient, UserClient userClient)
  {
    return new UserService(publicKey, authClient, userClient);
  }
}
