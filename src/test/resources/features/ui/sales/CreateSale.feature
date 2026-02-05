Feature: Create Sale

  @admin
  Scenario: TC_UI_SALES_01 - Verify successful sale
    When Admin clicks Sales in side navigation
    And Admin clicks Sell Plant button
    Then Sell Plant page should be displayed
    When Admin selects "Rose" plant
    And Admin enters quantity "5"
    And Admin clicks Sell button
    Then Sale should be created successfully
    And Admin should be redirected to Sales list page
    And Sales list should be visible
