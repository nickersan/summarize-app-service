package com.tn.summarize.app.client;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ElementClient", url = "${api.element.url}")
public interface ElementClient
{
  @GetMapping("/v1?parentId=null")
  List<Element> findRoots(@RequestParam("ownerId") String ownerId, @RequestParam("type") String type);

  @GetMapping("/v1")
  List<Element> findChildren(@RequestParam("parentId") long parentId, @RequestParam("ownerId") String ownerId, @RequestParam("type") String type);

  @PostMapping("/v1")
  Element create(ElementRequest element);

  @PutMapping("/v1/{elementId}")
  Element update(@PathVariable("elementId") long elementId, ElementRequest element);

  record Element(Long id, Long parentId, @NotNull String ownerId, @NotNull String type, @NotNull String name) {}

  record ElementRequest(Long parentId, @NotNull String ownerId, @NotNull String type, @NotNull String name) {}
}
