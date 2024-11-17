package com.tn.summarize.app.service;

import static com.tn.summarize.app.client.AuthClient.GenerateRequest;
import static com.tn.summarize.app.client.AuthClient.RefreshRequest;
import static com.tn.summarize.app.security.Jwt.subject;

import java.security.PublicKey;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.tn.summarize.app.client.AuthClient;
import com.tn.summarize.app.client.UserClient;
import com.tn.summarize.app.client.UserClient.UserRequest;
import com.tn.summarize.app.domain.User;
import com.tn.summarize.app.domain.UserAndTokens;

@Slf4j
@RequiredArgsConstructor
public class UserService
{
  private final PublicKey publicKey;
  private final AuthClient authClient;
  private final UserClient userClient;

  public String refreshAccessToken(String refreshToken)
  {
    var tokenSubject = subject(publicKey, refreshToken);

    log.debug("Refreshing access token for: {}", tokenSubject);

    var accessTokenResponse = authClient.refresh(new RefreshRequest(refreshToken));

    log.info("Refreshed access token for: {}", tokenSubject);

    return accessTokenResponse.access();
  }

  public void signIn(String email)
  {
    log.debug("Signing in user with email: {}", email);

    findForEmail(email).ifPresent(this::signIn);
  }

  public void signUp(String email, String firstName, String lastName)
  {
    log.debug("Signing up user with email: {}", email);

    var emailVerificationToken = generateEmailVerificationToken(email);
    var user = createOrUpdateUser(email, firstName, lastName, emailVerificationToken);

    //TODO: add call to email service.

    log.info("Signed up user with token-subject: {}", user.tokenSubject());
  }

  public UserAndTokens verifyEmail(String token)
  {
    var tokenSubject = subject(publicKey, token);

    log.debug("Verifying email for user with token-subject: {}", tokenSubject);

    var user = userClient.findForTokenSubject(tokenSubject).getFirst(); //.orElseThrow(UnauthorizedException::new);
    var tokenPair = authClient.generate(new GenerateRequest(user.email()));

    log.info("Verified email for user with token-subject: {}", tokenSubject);

    return new UserAndTokens(user, tokenPair.refresh(), tokenPair.access());
  }

  private void signIn(User user)
  {
    var emailVerificationToken = generateEmailVerificationToken(user.email());
    updateUser(user, emailVerificationToken);

    log.info("Signed in user with token-subject: {}", user.tokenSubject());
  }

  private User createOrUpdateUser(String email, String firstName, String lastName, String emailVerificationToken)
  {
    var user = findForEmail(email);

    return user.isEmpty() ?
      userClient.create(new UserRequest(email, firstName, lastName, subject(publicKey, emailVerificationToken))) :
      updateUser(user.get(), emailVerificationToken);
  }

  private User updateUser(User user, String emailVerificationToken)
  {
    var userRequest = new UserRequest(user.email(), user.firstName(), user.lastName(), subject(publicKey, emailVerificationToken));

    return userClient.update(user.id(), userRequest);
  }

  private Optional<User> findForEmail(String email)
  {
    var users = userClient.findForEmail(email);
    if (users.size() > 1) throw new IllegalStateException("Multiple users found for email: " + email);

    return Optional.ofNullable(users.isEmpty() ? null : users.getFirst());
  }

  private String generateEmailVerificationToken(String email)
  {
    var emailVerificationToken = authClient.generate(new GenerateRequest(email)).access();

    log.info("Generated email verification token: {} for email: {}", emailVerificationToken, email);

    return emailVerificationToken;
  }
}
