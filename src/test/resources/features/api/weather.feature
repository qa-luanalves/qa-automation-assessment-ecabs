Feature: Weather API

  Scenario: Should get weather for valid city
    Given the weather API is available
    When I request the current weather for "Malta"
    Then the response status should be 200

  Scenario: Should return 404 when requesting weather for an invalid city
    Given the weather API is available
    When I request the current weather for "ksksks"
    Then the response status should be 404
    And the error message should be "city not found"

  Scenario: Should retrieve 5-day forecast for a valid city
    Given the weather API is available
    When I request the 5 day forecast for "Malta"
    Then the response status should be 200
    And the forecast list should not be empty

  Scenario Outline: Validate city name boundary values
    Given the weather API is available
    When I request the current weather for "<city>"
    Then the response status should be <status>
    Examples:
      | city | status |
      | A                                                | 404 |
      |                                                  | 400 |
      | asdfghjklqwertyuiopzxcvbnmasdfghjklqwertyuiop    | 404 |
      | @@@@@                                            | 404 |