@UI_PlantAdd_AccessDenied
Feature: Plant Add Access Denied UI/UX

  @UI_PlantAdd_AccessDenied_010
  Scenario: Verify Normal User blocked from accessing Add Plant URL
    Given Normal User is logged in
    When User directly navigates to "/ui/plants/add"
    Then User is redirected to 403 Forbidden page
    And Page title shows "403 - Access Denied"
    And Error message "You do not have permission to access this page." is displayed
    And "Go Back" link is available
    And User cannot access Add Plant page
    And Authorization is enforced at URL level