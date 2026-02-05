Feature: View Sales List

  @nonadmin
  Scenario: TC_UI_SALES_07 - Verify User can view Sales list
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales list should be visible
