package com.tn.summarize.app.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tn.summarize.app.domain.Summary;

@Slf4j
@RestController
@RequestMapping("/v1/summary")
public class SummaryController
{
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Summary create(@AuthenticationPrincipal Jwt jwt, @RequestPart("file") MultipartFile file, @RequestParam(name = "folderId", required = false) Long folderId)
  {
    return new Summary(1L, file.getOriginalFilename());
  }
}
