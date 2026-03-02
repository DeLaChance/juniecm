package com.example.cm.configuration_item.service;

import com.example.cm.configuration_item.domain.ConfigurationItem;
import com.example.cm.configuration_item.domain.ConfigurationItemRelation;
import com.example.cm.configuration_item.domain.ConfigurationItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigurationItemService {
  private final ConfigurationItemRepository repository;

  public ConfigurationItemService(ConfigurationItemRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<ConfigurationItem> getAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public List<ConfigurationItemRelation> getAllRelations() {
    return repository.findAllRelations();
  }
}
