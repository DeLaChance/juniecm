package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import com.example.cm.configuration_item.domain.ConfigurationItemRelation;
import com.example.cm.configuration_item.service.ConfigurationItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/configuration_items")
class ConfigurationItemController {
  private final ConfigurationItemService service;

  public ConfigurationItemController(ConfigurationItemService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Map<String, List<ConfigurationItemDTO>>> getAll() {
    List<ConfigurationItem> items = service.getAll();
    List<ConfigurationItemRelation> relations = service.getAllRelations();

    Map<String, ConfigurationItem> itemMap = items.stream()
        .collect(Collectors.toMap(ConfigurationItem::getId, i -> i));

    Map<String, List<String>> parentToChildren = new HashMap<>();
    Set<String> allChildren = new HashSet<>();
    for (ConfigurationItemRelation relation : relations) {
      parentToChildren.computeIfAbsent(relation.getParentId(), k -> new ArrayList<>()).add(relation.getChildId());
      allChildren.add(relation.getChildId());
    }

    List<ConfigurationItem> rootItems = items.stream()
        .filter(i -> !allChildren.contains(i.getId()))
        .collect(Collectors.toList());

    List<ConfigurationItemDTO> dtos = rootItems.stream()
        .map(i -> buildDto(i, itemMap, parentToChildren))
        .collect(Collectors.toList());

    return ResponseEntity.ok(Collections.singletonMap("data", dtos));
  }

  private ConfigurationItemDTO buildDto(ConfigurationItem item, Map<String, ConfigurationItem> itemMap, Map<String, List<String>> parentToChildren) {
    List<String> childrenIds = parentToChildren.getOrDefault(item.getId(), Collections.emptyList());
    List<ConfigurationItemDTO> childrenDtos = childrenIds.stream()
        .map(id -> itemMap.get(id))
        .filter(childItem -> childItem != null)
        .map(childItem -> buildDto(childItem, itemMap, parentToChildren))
        .collect(Collectors.toList());

    return ConfigurationItemDTO.from(item, childrenDtos);
  }
}
