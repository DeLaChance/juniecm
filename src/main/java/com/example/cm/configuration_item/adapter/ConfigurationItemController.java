package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import com.example.cm.configuration_item.service.ConfigurationItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/configuration_items")
class ConfigurationItemController {

  private final ConfigurationItemService service;

  public ConfigurationItemController(ConfigurationItemService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ConfigurationItemDTO>> getAll() {
    List<ConfigurationItem> items = service.getAll();
    List<ConfigurationItemDTO> dtos = items.stream()
        .map(i -> ConfigurationItemDTO.from(i, Collections.emptyList()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }
}
