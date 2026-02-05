Feature: Pagination

  @nonadmin
  Scenario: TC_UI_SALES_11 - Verify pagination in sales list
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    And Pagination controls should be displayed
