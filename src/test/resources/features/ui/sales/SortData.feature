Feature: Sort Data

  @nonadmin
  Scenario: TC_UI_SALES_08 - Verify default sorting by Sold Date
    When User clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales should be sorted by Sold Date in descending order
