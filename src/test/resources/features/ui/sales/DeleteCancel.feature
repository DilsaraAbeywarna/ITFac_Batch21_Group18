Feature: Delete Confirmation

  @admin
  Scenario: TC_UI_SALES_05 - Verify Delete action shows confirmation prompt before deletion
    When Admin clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales list should be visible
    When Admin clicks Delete button for first sale
    Then Confirmation dialog should be displayed
    When Admin clicks Cancel on confirmation dialog
    Then Sale item should remain in the list
