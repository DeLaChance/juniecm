package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration.IntegrationTest;
import com.example.cm.configuration_item.domain.ConfigurationItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ConfigurationItemControllerIntegrationTest {

  @Autowired
  private RestTestClient restTestClient;

  @Test
  void shouldReturnConfigurationItems() {
    EntityExchangeResult<ConfigurationItem[]> response = restTestClient.get()
            .uri("/api/v1/configuration_items")
            .exchange()
            .returnResult(ConfigurationItem[].class);

    assertThat(response.getStatus().is2xxSuccessful()).isTrue();
    assertThat(response.getResponseBody()).hasSize(1);
  }
}
