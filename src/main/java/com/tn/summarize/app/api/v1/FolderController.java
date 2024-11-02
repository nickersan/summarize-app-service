package com.tn.summarize.app.api.v1;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.summarize.app.client.ElementClient;
import com.tn.summarize.app.domain.Folder;

@Slf4j
@RestController
@RequestMapping("/v1/folder")
@AllArgsConstructor
public class FolderController
{
  private static final String ELEMENT_TYPE = "FOLDER";

  private final ElementClient elementClient;

  @GetMapping
  List<Folder> roots(@AuthenticationPrincipal Jwt jwt)
  {
    return elementClient.findRoots(jwt.getSubject(), ELEMENT_TYPE).stream().map(this::folder).toList();
  }

  @GetMapping("/{parentId}")
  List<Folder> children(@AuthenticationPrincipal Jwt jwt, @PathVariable("parentId") long parentId)
  {
    return elementClient.findChildren(parentId, jwt.getSubject(), ELEMENT_TYPE).stream().map(this::folder).toList();
  }

  @PostMapping
  Folder create(@AuthenticationPrincipal Jwt jwt, @RequestBody FolderRequest folderRequest)
  {
    return folder(
      elementClient.create(
        new ElementClient.ElementRequest(
          folderRequest.parentId,
          jwt.getSubject(),
          ELEMENT_TYPE,
          folderRequest.name()
        )
      )
    );
  }

  private Folder folder(ElementClient.Element element)
  {
    return new Folder(element.id(), element.parentId(), element.ownerId(), element.name());
  }

  public record FolderRequest(Long parentId, @NotNull String name) {}
}
