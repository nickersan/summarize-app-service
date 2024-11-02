package com.tn.summarize.app.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException
{
  public UnauthorizedException() {}

  public UnauthorizedException(Throwable cause)
  {
    super(cause);
  }
}
