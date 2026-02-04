
Feature: View Sales List

  Scenario: Verify user can view sales list
    Given Test user is logged in
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales list should be visible
