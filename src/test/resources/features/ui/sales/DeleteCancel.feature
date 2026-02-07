Feature: Delete Cancel

  @admin
  Scenario: TC_UI_SALES_05 - Verify Delete confirmation dialog can be canceled
    When Admin clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales list should be visible
    When Admin clicks Delete button to test cancel
    Then Delete confirmation dialog is displayed
    When Admin cancels the delete confirmation
    Then Sale item remains in the list
