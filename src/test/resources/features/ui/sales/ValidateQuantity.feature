@admin
Feature: Validate Quantity

  Scenario: TC_UI_SALES_02 - Verify quantity validation
    When Admin clicks Sales in side navigation
    And Admin clicks Sell Plant button
    Then Sell Plant page should be displayed
    When Admin selects "Rose" plant
    And Admin enters quantity "0"
    And Admin clicks Sell button
    Then Validation message "Value must be greater than or equal to 1." should be displayed
