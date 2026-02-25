Feature: Form Submission

  Scenario: Submit form with valid data
    Given I am on the Practice Form page
    When I fill out the practice form with valid data
    And I submit the practice form
    Then I should see the confirmation modal

  Scenario: Required fields validation
    Given I am on the Practice Form page
    When I try to submit the form without filling mandatory fields
    Then the mandatory fields should be required