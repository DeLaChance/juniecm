package com.example.cm.configuration_item.domain;

public class ConfigurationItemRelation {
  private final String parentId;
  private final String childId;

  public ConfigurationItemRelation(String parentId, String childId) {
    this.parentId = parentId;
    this.childId = childId;
  }

  public String getParentId() { return parentId; }
  public String getChildId() { return childId; }

  public static ConfigurationItemRelation from(java.util.Map<String, Object> map) {
    return new ConfigurationItemRelation(
        (String) map.get("parent_id"),
        (String) map.get("child_id")
    );
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String parentId;
    private String childId;

    public Builder parentId(String parentId) { this.parentId = parentId; return this; }
    public Builder childId(String childId) { this.childId = childId; return this; }

    public ConfigurationItemRelation build() {
      return new ConfigurationItemRelation(parentId, childId);
    }
  }
}
