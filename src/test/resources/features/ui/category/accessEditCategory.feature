Feature: Edit Category access control

  @access-edit
  Scenario: Verify non-admin user cannot access Edit Category page via direct URL
    Given User is logged in as non-admin user
    When User enters "/ui/categories/edit/{id}" directly in the browser address bar
    And User presses Enter
    Then User should be redirected to 403 or dashboard and see access denied message
    And Edit Category page with id "dynamic" is not displayed

  @edit-button-visibility
  Scenario: Verify Edit action is not visible to non-admin user
    Given User is logged in as non-admin user
    And At least one category exists in the system
    When User navigates to Category List page
    Then Category List page is displayed
    And Edit action is not visible for all categories
    And User cannot initiate category edit via UI

