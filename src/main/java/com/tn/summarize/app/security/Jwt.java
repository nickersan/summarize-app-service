package com.tn.summarize.app.security;

import java.security.PublicKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class Jwt
{
  private static final String CLAIM_EMAIL = "email";
  private static final String HEADER_TYPE = "typ";
  private static final String TOKEN_START = " ";
  private static final String TYPE_JWT = "JWT";

  private Jwt() {}

  public static org.springframework.security.oauth2.jwt.Jwt forToken(PublicKey publicKey, String token)
  {
    var jws = checkAuthorization(publicKey, token);

    return org.springframework.security.oauth2.jwt.Jwt
      .withTokenValue(token)
      .header(HEADER_TYPE, TYPE_JWT)
      .subject(jws.getPayload().getSubject())
      .issuer(jws.getPayload().getIssuer())
      .issuedAt(jws.getPayload().getIssuedAt().toInstant())
      .expiresAt(jws.getPayload().getExpiration().toInstant())
      .claims(claims -> claims.put(CLAIM_EMAIL, jws.getPayload().get(CLAIM_EMAIL)))
      .build();
  }

  public static String subject(PublicKey publicKey, String token)
  {
    return checkAuthorization(publicKey, token).getPayload().getSubject();
  }

  private static Jws<Claims> checkAuthorization(PublicKey publicKey, String token)
  {
    try
    {
      return Jwts.parser()
        .verifyWith(publicKey)
        .build()
        .parseSignedClaims(cleanPrefix(token));
    }
    catch (Exception e)
    {
      throw new UnauthorizedException(e);
    }
  }

  private static String cleanPrefix(String token)
  {
    var tokenStartIndex = token.indexOf(TOKEN_START);
    return (tokenStartIndex > -1 ? token.substring(tokenStartIndex) : token).trim();
  }
}
