package com.tn.summarize.app.client;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "AuthClient", url = "${api.auth.url}", configuration = ProxyErrorDecoder.class)
public interface AuthClient
{
  @PostMapping("/v1/generate")
  TokenPair generate(GenerateRequest request);

  @PostMapping("/v1/refresh")
  TokenPair refresh(RefreshRequest request);

  record GenerateRequest(String email) {}

  record TokenPair(@NonNull String refresh, @NonNull String access) {}

  record RefreshRequest(String refreshToken) {}
}
