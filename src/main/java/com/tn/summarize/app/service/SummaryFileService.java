package com.tn.summarize.app.service;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import com.tn.summarize.app.client.FileClient;

@RequiredArgsConstructor
public class SummaryFileService
{
  private static final String TEMPLATE_KEY = "%s.%s";

  private final FileClient fileClient;

  public void update(long summaryId, String type, MultipartFile file)
  {
    fileClient.upsert(format(TEMPLATE_KEY, summaryId, type), file);
  }
}
