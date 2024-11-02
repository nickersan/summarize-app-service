package com.tn.summarize.app.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ProxyErrorDecoder implements ErrorDecoder
{
  @Override
  public Exception decode(String methodKey, Response response)
  {
    log.error("{} received: {}", methodKey, response.status());
    return new ResponseStatusException(HttpStatusCode.valueOf(response.status()), response.reason());
  }

  @Configuration
  public static class Config
  {
    @Bean
    ErrorDecoder errorDecoder()
    {
      return new ProxyErrorDecoder();
    }
  }
}
