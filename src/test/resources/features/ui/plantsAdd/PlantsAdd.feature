Feature: Add Plant Button Visibility

  Scenario: Admin sees Add Plant button on plant list page
    Given Admin is logged in
    And Admin is on plant list page
    Then Plant list page should be displayed
    When Admin observes the "Add Plant" button
    Then "Add Plant" button should be visible
    And "Add Plant" button should be enabled and clickable