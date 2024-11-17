package com.tn.summarize.app.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tn.summarize.app.service.SummaryFileService;

@Slf4j
@RestController
@RequestMapping("/v1/summary")
@RequiredArgsConstructor
public class SummaryFileController
{
  private final SummaryFileService summaryFileService;

  @PutMapping(path = "/{summaryId}/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  void update(@PathVariable("summaryId") long summaryId, @PathVariable("type") String type, @RequestPart("file") MultipartFile file)
  {
    summaryFileService.update(summaryId, type, file);
  }
}
