package com.tn.summarize.app.client;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tn.summarize.app.domain.Summary;

@FeignClient(value = "SummaryClient", url = "${api.summary.url}")
public interface SummaryClient
{
  @GetMapping("/v1?folderId=null")
  List<Summary> find(@RequestParam("userId") String userId);

  @GetMapping("/v1")
  List<Summary> find(@RequestParam("userId") String userId, @RequestParam("folderId") long folderId);

  @PostMapping("/v1")
  Summary create(SummaryClient.SummaryRequest summary);

  @PutMapping("/v1/{summaryId}")
  Summary update(@PathVariable("summaryId") long summaryId, SummaryRequest summaryRequest);

  record SummaryRequest(@NotNull String userId, @Nullable Long folderId, @NotNull String name) {}
}
