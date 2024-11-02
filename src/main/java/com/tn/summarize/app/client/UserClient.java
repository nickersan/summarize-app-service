package com.tn.summarize.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tn.summarize.app.domain.User;

@FeignClient(value = "UserClient", url = "${api.user.url}")
public interface UserClient
{
  @GetMapping("/v1")
  List<User> findForEmail(@RequestParam("email") String email);

  @GetMapping("/v1")
  List<User> findForTokenSubject(@RequestParam("tokenSubject") String tokenSubject);

  @PostMapping("/v1")
  User create(UserRequest user);

  @PutMapping("/v1/{userId}")
  User update(@PathVariable("userId") long userId, UserRequest user);

  record UserRequest(String email, String firstName, String lastName, String tokenSubject) {}
}
