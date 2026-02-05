Feature: Cancel Sale Navigation

  @admin
  Scenario: TC_UI_SALES_06 - Verify cancel button navigation
    When Admin clicks Sales in side navigation
    And Admin clicks Sell Plant button
    Then Sell Plant page should be displayed
    When Admin clicks Cancel button
    Then Admin should be redirected to Sales list page
    And Sales list should be visible
