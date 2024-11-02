package com.tn.summarize.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Application
{
  public static void main(String[] args)
  {
    new SpringApplication(Application.class).run(args);
  }
}
