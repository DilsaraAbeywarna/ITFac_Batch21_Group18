Feature: Plant Edit Navigation

  @admin
  Scenario: UI_PLANTEDIT_NAVIGATION_01 - Navigate to Edit Plant page
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    Then Admin user should be navigated to the Edit Plant page
