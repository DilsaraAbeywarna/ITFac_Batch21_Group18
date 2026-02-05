Feature: Sort by Quantity

  @nonadmin
  Scenario: TC_UI_SALES_10 - Verify sorting by Quantity
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    When User clicks Quantity column header
    Then Sales should be sorted by Quantity in ascending order
    When User clicks Quantity column header again
    Then Sales should be sorted by Quantity in descending order
