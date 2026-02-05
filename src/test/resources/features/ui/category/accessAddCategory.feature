Feature: Add Category access control

  @access-add
  Scenario: Verify non-admin user cannot access Add Category page via direct URL
    Given User is logged in as non-admin user
    When User enters "/ui/categories/add" directly in the browser address bar
    And User presses Enter
    Then User should be redirected to 403 or dashboard and see access denied message
    And Add Category page is not displayed


  @button-visibility
  Scenario: Verify Add Category button is not visible to non-admin user
    Given User is logged in as non-admin user
    When User navigates to Category List page
    Then Category List page is displayed
    And Add Category button is not visible to the user
    And User is unable to navigate to Add Category page via UI

  
@cancel-navigation
Scenario: Verify non-admin user cannot access Add Category page via direct URL
  Given User is logged in as non-admin user
  When User enters "/ui/categories/add" directly in the browser address bar
  And User presses Enter
  Then User should be redirected to 403 or dashboard
  And Add Category page is not displayed
  And Cancel button is not visible
  And Category list remains unchanged after blocked access