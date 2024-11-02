package com.tn.summarize.app.config;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.tn.summarize.app.security.Jwt;

@Configuration
class SecurityConfiguration
{
  private static final String PATTERN_V1_AUTH = "/v1/auth/**";

  @Bean
  PublicKey publicKey(
    @Value("${summarize.app-service.token.signing.public-key}") String publicKey
  )
    throws NoSuchAlgorithmException, InvalidKeySpecException
  {
    var decodedPublicKey = Base64.getDecoder().decode(publicKey.replaceAll("\\s+", "").trim());

    return KeyFactory
      .getInstance("RSA")
      .generatePublic(new X509EncodedKeySpec(decodedPublicKey));
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, PublicKey publicKey) throws Exception
  {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(configureRequestMatchers())
      .oauth2ResourceServer(configureResourceServer(publicKey))
      .build();
  }

  private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> configureRequestMatchers()
  {
    return registry -> registry
      .requestMatchers(PATTERN_V1_AUTH).permitAll()
      .anyRequest().authenticated();
  }

  private Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> configureResourceServer(PublicKey publicKey)
  {
    return configurer -> configurer.jwt(jwtCustomizer -> jwtCustomizer.decoder(token -> Jwt.forToken(publicKey, token)));
  }
}
