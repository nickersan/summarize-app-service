package com.tn.summarize.app.client;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.tn.summarize.app.config.FeignConfiguration;

@FeignClient(value = "FileClient", url = "${api.file.url}", configuration = FeignConfiguration.class)
public interface FileClient
{
  @PutMapping(value = "/v1/{key}", consumes = MULTIPART_FORM_DATA_VALUE)
  void upsert(@PathVariable("key") String key, @RequestPart(value = "file") MultipartFile file);
}
