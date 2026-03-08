package com.example.cm.configuration_item.domain;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
class JdbcConfigurationItemRepository implements ConfigurationItemRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String findAllSql;
  private final String findAllRelationsSql;

  public JdbcConfigurationItemRepository(
      NamedParameterJdbcTemplate jdbcTemplate,
      @Value("classpath:sql/findAllConfigurationItems.sql") Resource findAllResource,
      @Value("classpath:sql/findAllConfigurationItemRelations.sql") Resource findAllRelationsResource) throws IOException {
    this.jdbcTemplate = jdbcTemplate;
    this.findAllSql = findAllResource.getContentAsString(StandardCharsets.UTF_8);
    this.findAllRelationsSql = findAllRelationsResource.getContentAsString(StandardCharsets.UTF_8);
  }

  @Override
  public List<ConfigurationItem> findAll() {
    return jdbcTemplate.queryForList(findAllSql, new MapSqlParameterSource())
            .stream().map(ConfigurationItem::from).toList();
  }

  @Override
  public List<ConfigurationItemRelation> findAllRelations() {
    return jdbcTemplate.queryForList(findAllRelationsSql, new MapSqlParameterSource())
        .stream().map(ConfigurationItemRelation::from).toList();
  }
}
