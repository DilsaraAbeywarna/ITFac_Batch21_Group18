Feature: No Sales Records

  @nonadmin
  Scenario: TC_UI_SALES_09 - Verify empty sales message
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    And "No sales found" message should be displayed
