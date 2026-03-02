package com.example.cm.configuration_item.domain;

import java.util.List;

public interface ConfigurationItemRepository {
  List<ConfigurationItem> findAll();
  List<ConfigurationItemRelation> findAllRelations();
}
