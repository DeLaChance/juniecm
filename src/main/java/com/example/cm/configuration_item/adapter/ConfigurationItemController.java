package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import com.example.cm.configuration_item.domain.ConfigurationItemRelation;
import com.example.cm.configuration_item.service.ConfigurationItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configuration_items")
class ConfigurationItemController {
  private final ConfigurationItemService service;

  public ConfigurationItemController(ConfigurationItemService service) {
    this.service = service;
  }

  @GetMapping("/overview")
  public ResponseEntity<ConfigurationItemsOverviewDTO> getAll() {
    List<ConfigurationItem> items = service.getAll();
    List<ConfigurationItemRelation> relations = service.getAllRelations();

    ConfigurationItemsOverviewDTO overview = ConfigurationItemsOverviewDTO.from(items, relations);

    return ResponseEntity.ok(overview);
  }
}
