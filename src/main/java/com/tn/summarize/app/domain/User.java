package com.tn.summarize.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record User(Long id, String email, String firstName, String lastName, @JsonIgnore String tokenSubject)
{
  public User(String email, String firstName, String lastName, String tokenSubject)
  {
    this(null, email, firstName, lastName, tokenSubject);
  }
}
