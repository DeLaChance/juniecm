package com.example.cm.configuration_item.domain;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    return jdbcTemplate.query(findAllSql, (rs, rowNum) -> {
      return ConfigurationItem.builder()
          .id(rs.getString("id"))
          .name(rs.getString("name"))
          .type(rs.getString("type"))
          .startDate(rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null)
          .endDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null)
          .ownerId(rs.getString("owner_id"))
          .build();
    });
  }

  @Override
  public List<ConfigurationItemRelation> findAllRelations() {
    return jdbcTemplate.query(findAllRelationsSql, (rs, rowNum) -> {
      return ConfigurationItemRelation.builder()
          .parentId(rs.getString("parent_id"))
          .childId(rs.getString("child_id"))
          .build();
    });
  }
}
