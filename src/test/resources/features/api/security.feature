Feature: Weather API Security Validation

  Scenario: Should return 401 when API key is invalid
    Given the weather API is available
    When I request the current weather with invalid API key for "Malta"
    Then the response status should be 401

  Scenario: Should return 401 when API key is missing
    Given the weather API is available
    When I request the current weather without API key for "Malta"
    Then the response status should be 401

  Scenario: Should handle injection attempt safely
    Given the weather API is available
    When I request the current weather for "' OR 1=1 --"
    Then the response status should be 404