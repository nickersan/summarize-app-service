package com.tn.summarize.app.service;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import com.tn.summarize.app.client.ElementClient;
import com.tn.summarize.app.domain.Folder;

@RequiredArgsConstructor
public class FolderService
{
  private static final String ELEMENT_TYPE = "FOLDER";

  private final ElementClient elementClient;

  public List<Folder> roots(@NotNull String userId)
  {
    return elementClient.findRoots(userId, ELEMENT_TYPE).stream().map(this::folder).toList();
  }

  public List<Folder> children(@NotNull String userId, long parentId)
  {
    return elementClient.findChildren(userId, parentId, ELEMENT_TYPE).stream().map(this::folder).toList();
  }

  public Folder create(@NotNull String userId, @Nullable Long parentId, @NotNull String name)
  {
    return folder(
      elementClient.create(
        new ElementClient.ElementRequest(
          userId,
          parentId,
          ELEMENT_TYPE,
          name
        )
      )
    );
  }

  private Folder folder(ElementClient.Element element)
  {
    return new Folder(element.id(), element.parentId(), element.ownerId(), element.name());
  }
}
