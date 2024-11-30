package com.tn.summarize.app.service;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import com.tn.summarize.app.client.SummaryClient;
import com.tn.summarize.app.domain.Summary;

@RequiredArgsConstructor
public class SummaryService
{
  private final SummaryClient summaryClient;

  public List<Summary> get(@NotNull String userId)
  {
    return summaryClient.find(userId);
  }

  public List<Summary> get(@NotNull String userId, @Nullable Long folderId)
  {
    return folderId != null ? summaryClient.find(userId, folderId) : summaryClient.find(userId);
  }

  public Summary create(@NotNull String userId, @Nullable Long folderId, @NotNull String name)
  {
    return summaryClient.create(
      new SummaryClient.SummaryRequest(
        userId,
        folderId,
        name
      )
    );
  }

  public Summary update(@Nonnull String userId, long summaryId, @Nullable Long folderId, @NotNull String name)
  {
    return summaryClient.update(
      summaryId,
      new SummaryClient.SummaryRequest(
        userId,
        folderId,
        name
      )
    );
  }
}
