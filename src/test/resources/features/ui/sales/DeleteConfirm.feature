@admin
Feature: Delete Confirmation

  Scenario: TC_UI_SALES_005 - Verify Delete action shows confirmation prompt before deletion
    When Admin clicks Sales in side navigation
    Then Sales page should load successfully
    And Sales list should be visible
    When Admin clicks Delete button for a sale
    Then Confirmation dialog should be displayed
    When Admin clicks OK on confirmation dialog
    Then Sale deleted successfully message should be displayed
