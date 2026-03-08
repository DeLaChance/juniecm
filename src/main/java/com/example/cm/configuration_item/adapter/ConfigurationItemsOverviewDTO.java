package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import com.example.cm.configuration_item.domain.ConfigurationItemRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigurationItemsOverviewDTO {
  private List<ConfigurationItemDTO> data;
  private long totalCount;

  public ConfigurationItemsOverviewDTO() {
  }

  public ConfigurationItemsOverviewDTO(List<ConfigurationItemDTO> data, long totalCount) {
    this.data = data;
    this.totalCount = totalCount;
  }

  public List<ConfigurationItemDTO> getData() {
    return data;
  }

  public void setData(List<ConfigurationItemDTO> data) {
    this.data = data;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public static ConfigurationItemsOverviewDTO from(List<ConfigurationItem> items, List<ConfigurationItemRelation> relations) {
    Map<String, ConfigurationItem> itemMap = items.stream()
        .collect(Collectors.toMap(ConfigurationItem::getId, i -> i));

    Map<String, List<String>> parentToChildren = new HashMap<>();
    Set<String> allChildren = new HashSet<>();
    for (ConfigurationItemRelation relation : relations) {
      parentToChildren.computeIfAbsent(relation.getParentId(), k -> new ArrayList<>()).add(relation.getChildId());
      allChildren.add(relation.getChildId());
    }

    List<ConfigurationItemDTO> rootDtos = items.stream()
        .filter(i -> !allChildren.contains(i.getId()))
        .map(i -> buildNestedDto(i, itemMap, parentToChildren))
        .collect(Collectors.toList());

    return new ConfigurationItemsOverviewDTO(rootDtos, items.size());
  }

  private static ConfigurationItemDTO buildNestedDto(ConfigurationItem item, Map<String, ConfigurationItem> itemMap, Map<String, List<String>> parentToChildren) {
    List<String> childrenIds = parentToChildren.getOrDefault(item.getId(), Collections.emptyList());
    List<ConfigurationItemDTO> childrenDtos = childrenIds.stream()
        .map(id -> itemMap.get(id))
        .filter(java.util.Objects::nonNull)
        .map(childItem -> buildNestedDto(childItem, itemMap, parentToChildren))
        .collect(Collectors.toList());

    return ConfigurationItemDTO.from(item, childrenDtos);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private List<ConfigurationItemDTO> data;
    private long totalCount;

    public Builder data(List<ConfigurationItemDTO> data) {
      this.data = data;
      return this;
    }

    public Builder totalCount(long totalCount) {
      this.totalCount = totalCount;
      return this;
    }

    public ConfigurationItemsOverviewDTO build() {
      return new ConfigurationItemsOverviewDTO(data, totalCount);
    }
  }
}
