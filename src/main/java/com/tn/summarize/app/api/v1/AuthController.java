package com.tn.summarize.app.api.v1;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.summarize.app.domain.UserAndTokens;
import com.tn.summarize.app.service.UserService;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
  private final UserService userService;

  @PostMapping("/sign-up")
  public void signUp(@RequestBody SignUpRequest request)
  {
    userService.signUp(request.email(), request.firstName(), request.lastName());
  }

  @PostMapping("/sign-in")
  public void signIn(@RequestBody SignInRequest request)
  {
    userService.signIn(request.email());
  }

  @PostMapping("/email-verify")
  public UserAndTokens verifyEmail(@RequestBody VerifyEmailRequest request)
  {
    return userService.verifyEmail(request.token());
  }

  @PostMapping("/refresh")
  public AccessTokenResponse refresh(@RequestBody RefreshAccessTokenRequest request)
  {
    var accessToken = userService.refreshAccessToken(request.refreshToken());
    return new AccessTokenResponse(accessToken);
  }

  public record SignUpRequest(
    @NotEmpty(message = "An email is required.")
    @Email(message = "An email is not valid.")
    String email,
    @NotEmpty(message = "A first name is required.")
    String firstName,
    @NotEmpty(message = "A last name is required.")
    String lastName
  ) {}

  public record SignInRequest(
    @NotEmpty(message = "An email is required.")
    @Email(message = "An email is not valid.")
    String email
  ) {}

  public record VerifyEmailRequest(
    @NotEmpty(message = "A token is required.")
    String token
  ) {}


  public record RefreshAccessTokenRequest(String refreshToken) {}

  public record AccessTokenResponse(String accessToken) {}
}
