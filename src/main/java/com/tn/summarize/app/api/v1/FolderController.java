package com.tn.summarize.app.api.v1;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tn.summarize.app.domain.Folder;
import com.tn.summarize.app.service.FolderService;

@Slf4j
@RestController
@RequestMapping("/v1/folder")
@RequiredArgsConstructor
public class FolderController
{
  private final FolderService folderService;

  @GetMapping
  List<Folder> roots(@AuthenticationPrincipal Jwt jwt)
  {
    return folderService.roots(jwt.getSubject());
  }

  @GetMapping("/{parentId}")
  List<Folder> children(@AuthenticationPrincipal Jwt jwt, @PathVariable("parentId") long parentId)
  {
    return folderService.children(jwt.getSubject(), parentId);
  }

  @PostMapping
  Folder create(@AuthenticationPrincipal Jwt jwt, @RequestBody FolderRequest folderRequest)
  {
    return folderService.create(
      jwt.getSubject(),
      folderRequest.parentId,
      folderRequest.name()
    );
  }

  public record FolderRequest(@Nullable Long parentId, @NotNull String name) {}
}
