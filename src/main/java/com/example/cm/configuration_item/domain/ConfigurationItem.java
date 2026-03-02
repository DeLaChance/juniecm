package com.example.cm.configuration_item.domain;

import java.time.LocalDateTime;
import java.util.Map;

public class ConfigurationItem {
  private final String id;
  private final String name;
  private final String type;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String ownerId;

  public ConfigurationItem(String id, String name, String type, LocalDateTime startDate, LocalDateTime endDate, String ownerId) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerId = ownerId;
  }

  public String getId() { return id; }
  public String getName() { return name; }
  public String getType() { return type; }
  public LocalDateTime getStartDate() { return startDate; }
  public LocalDateTime getEndDate() { return endDate; }
  public String getOwnerId() { return ownerId; }

  public static ConfigurationItem from(Map<String, Object> map) {
    return new ConfigurationItem(
        (String) map.get("id"),
        (String) map.get("name"),
        (String) map.get("type"),
        map.get("start_date") != null ? LocalDateTime.parse(map.get("start_date").toString().replace(" ", "T")) : null,
        map.get("end_date") != null ? LocalDateTime.parse(map.get("end_date").toString().replace(" ", "T")) : null,
        (String) map.get("owner_id")
    );
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String name;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String ownerId;

    public Builder id(String id) { this.id = id; return this; }
    public Builder name(String name) { this.name = name; return this; }
    public Builder type(String type) { this.type = type; return this; }
    public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
    public Builder endDate(LocalDateTime endDate) { this.endDate = endDate; return this; }
    public Builder ownerId(String ownerId) { this.ownerId = ownerId; return this; }

    public ConfigurationItem build() {
      return new ConfigurationItem(id, name, type, startDate, endDate, ownerId);
    }
  }
}
