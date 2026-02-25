Feature: Weather API Integration Validation

  Scenario: Current weather should match today's forecast
    Given the weather API is available
    When I retrieve the current weather and forecast for "Malta"
    Then today's forecast temperature should be close to current weather temperature