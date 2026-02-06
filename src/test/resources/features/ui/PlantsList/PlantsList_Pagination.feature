@UI_PlantList_Pagination
Feature: Plant List Pagination Navigation UI/UX

  @UI_PlantList_PaginationNavigation_006
  Scenario: Normal User can navigate through pagination
    Given Normal User is logged in
    And More than 10 plants exist
    And User is on plant list page for pagination    
    Then Plant list page is displayed for pagination 
    When User observes first page of plants
    Then First page shows plants 1-10 with pagination controls
    When User clicks "Next" button
    Then Next button is clicked
    And User observes second page
    Then Second page loads showing plants 11-20
    And Page indicator updates to page 2            
    When User clicks "Previous" button
    Then Previous button is clicked
    And First page loads again
    And Page indicator updates to page 1        
    And Pagination navigation works correctly        