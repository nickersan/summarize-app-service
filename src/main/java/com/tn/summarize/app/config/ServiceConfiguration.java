package com.tn.summarize.app.config;

import java.security.PublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tn.summarize.app.client.AuthClient;
import com.tn.summarize.app.client.ElementClient;
import com.tn.summarize.app.client.FileClient;
import com.tn.summarize.app.client.SummaryClient;
import com.tn.summarize.app.client.UserClient;
import com.tn.summarize.app.service.FolderService;
import com.tn.summarize.app.service.SummaryFileService;
import com.tn.summarize.app.service.SummaryService;
import com.tn.summarize.app.service.UserService;

@Configuration
class ServiceConfiguration
{
  @Bean
  FolderService folderService(ElementClient elementClient)
  {
    return new FolderService(elementClient);
  }

  @Bean
  SummaryService summaryService(SummaryClient summaryClient)
  {
    return new SummaryService(summaryClient);
  }

  @Bean
  SummaryFileService summaryFileService(FileClient fileClient)
  {
    return new SummaryFileService(fileClient);
  }

  @Bean
  UserService userService(PublicKey publicKey, AuthClient authClient, UserClient userClient)
  {
    return new UserService(publicKey, authClient, userClient);
  }
}
