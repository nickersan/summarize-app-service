package com.tn.summarize.app.domain;

public record Folder(Long id, Long parentId, String userId, String name)
{
  public Folder(String userId, String name)
  {
    this(null, null, userId, name);
  }

  public Folder(Long parentId, String userId, String name)
  {
    this(null, parentId, userId, name);
  }
}
