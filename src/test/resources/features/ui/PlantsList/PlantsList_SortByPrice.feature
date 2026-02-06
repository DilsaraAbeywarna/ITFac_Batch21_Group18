@UI_PlantList_SortingByPrice
Feature: Plant List Sorting by Price UI/UX

  @UI_PlantList_SortingByPrice_009
  Scenario: Verify Normal User can sort plants by price
    Given Normal User is logged in
    And Multiple plants with different prices exist
    And User is on plant list page for sorting
    Then Plant list page is displayed for sorting
    When User clicks on Price column header
    Then Plants are sorted by price in ascending order
    And Lowest price appears first
    When User clicks on Price column header again
    Then Plants are sorted by price in descending order
    And Highest price appears first
    And Sorting functionality works correctly