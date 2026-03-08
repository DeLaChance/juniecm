package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration.IntegrationTest;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.ExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ConfigurationItemControllerIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(ConfigurationItemControllerIntegrationTest.class);

  @Autowired
  private RestTestClient restTestClient;

  @Test
  void shouldReturnConfigurationItems() {
    EntityExchangeResult<ConfigurationItemDTO[]> response = restTestClient.get()
            .uri("/api/v1/configuration_items")
            .exchange()
            .returnResult(ConfigurationItemDTO[].class);
    ConfigurationItemDTO[] items = response.getResponseBody();

      assertThat(response.getStatus().is2xxSuccessful()).isTrue();
      assertThat(items).hasSize(3);
  }
}
