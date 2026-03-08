package com.example.cm.configuration_item.adapter;

import com.example.cm.configuration.IntegrationTest;
import com.example.cm.configuration_item.domain.ConfigurationItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ConfigurationItemControllerIntegrationTest {

  @Autowired
  private RestTestClient restTestClient;

  @Test
  void shouldReturnConfigurationItems() {
    EntityExchangeResult<ConfigurationItemsOverviewDTO> response = restTestClient.get()
      .uri("/api/v1/configuration_items/overview")
      .exchange()
      .returnResult(ConfigurationItemsOverviewDTO.class);

    assertThat(response.getStatus().is2xxSuccessful()).isTrue();
    ConfigurationItemsOverviewDTO body = response.getResponseBody();
    assertThat(body).isNotNull();
    assertThat(body.getData()).isNotEmpty();
    assertThat(body.getTotalCount()).isEqualTo(3);

    ConfigurationItemDTO firstElement = body.getData().get(0);
    assertThat(firstElement.getId()).isEqualTo("ABC01");
    assertThat(firstElement.getName()).isEqualTo("Laptop Lucien");
    assertThat(firstElement.getType()).isEqualTo("hardware");
    assertThat(firstElement.getStartDate()).isEqualTo(LocalDateTime.of(2026, 3, 1, 8, 32, 1));
    assertThat(firstElement.getEndDate()).isNull();
    assertThat(firstElement.getChildren()).isNotEmpty();

    ConfigurationItemDTO firstChild = firstElement.getChildren().getFirst();
    assertThat(firstChild.getId()).isEqualTo("ABC02");
  }
}
