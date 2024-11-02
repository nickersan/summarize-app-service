package com.tn.summarize.app.api.v1;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.summarize.app.client.UserClient;
import com.tn.summarize.app.domain.User;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController
{
  private final UserClient userClient;

  @GetMapping
  public User get(@AuthenticationPrincipal Jwt jwt)
  {
    var users = userClient.findForTokenSubject(jwt.getSubject());
    return users.isEmpty() ? null : users.getFirst();
  }
}
