package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConfigurationItemDTO {
  private final String id;
  private final String name;
  private final String type;
  private final String startDate;
  private final String endDate;
  private final List<ConfigurationItemDTO> children;

  public ConfigurationItemDTO(String id, String name, String type, String startDate, String endDate, List<ConfigurationItemDTO> children) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.children = children;
  }

  public String getId() { return id; }
  public String getName() { return name; }
  public String getType() { return type; }
  public String getStartDate() { return startDate; }
  public String getEndDate() { return endDate; }
  public List<ConfigurationItemDTO> getChildren() { return children; }

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  public static ConfigurationItemDTO from(ConfigurationItem entity, List<ConfigurationItemDTO> children) {
    return new ConfigurationItemDTO(
        entity.getId(),
        entity.getName(),
        entity.getType(),
        entity.getStartDate() != null ? entity.getStartDate().format(FORMATTER) : null,
        entity.getEndDate() != null ? entity.getEndDate().format(FORMATTER) : null,
        children
    );
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String name;
    private String type;
    private String startDate;
    private String endDate;
    private List<ConfigurationItemDTO> children;

    public Builder id(String id) { this.id = id; return this; }
    public Builder name(String name) { this.name = name; return this; }
    public Builder type(String type) { this.type = type; return this; }
    public Builder startDate(String startDate) { this.startDate = startDate; return this; }
    public Builder endDate(String endDate) { this.endDate = endDate; return this; }
    public Builder children(List<ConfigurationItemDTO> children) { this.children = children; return this; }

    public ConfigurationItemDTO build() {
      return new ConfigurationItemDTO(id, name, type, startDate, endDate, children);
    }
  }
}
