package com.tn.summarize.app.api.v1;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tn.summarize.app.domain.Summary;
import com.tn.summarize.app.service.SummaryService;

@Slf4j
@RestController
@RequestMapping("/v1/summary")
@RequiredArgsConstructor
public class SummaryController
{
  private final SummaryService summaryService;

  @GetMapping
  List<Summary> get(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "folderId", required = false) Long folderId)
  {
    return folderId != null ? summaryService.get(jwt.getSubject(), folderId) : summaryService.get(jwt.getSubject());
  }

  @PostMapping
  Summary create(@AuthenticationPrincipal Jwt jwt, @RequestBody SummaryController.SummaryRequest summaryRequest)
  {
    return summaryService.create(
      jwt.getSubject(),
      summaryRequest.folderId,
      summaryRequest.name()
    );
  }

  public record SummaryRequest(@Nullable Long folderId, @NotNull String name) {}
}
