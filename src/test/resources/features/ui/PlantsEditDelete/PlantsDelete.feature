Feature: Plant Delete

  @testuser
  Scenario: UI_PLANTDELETE_DELETEBUTTON_13 - Verify Delete button visibility for test user role
    Given Test User is logged in and has navigated to the Plant page for delete
    When Test User navigates to the Plant List page for delete
    Then Delete button is not displayed for the Test User role
