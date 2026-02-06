@UI_PlantList_FilterByCategory
Feature: Plant List Filter by Category UI/UX

  @UI_PlantList_FilterByCategory_008
  Scenario: Verify Normal User can filter plants by category
    Given Normal User is logged in
    And Plants exist in different categories
    And User is on plant list page for filter
    Then Plant list page is displayed for filter
    When User selects "Foliage" from category dropdown
    And User clicks Search button for filter
    When User validates category filtered results
    Then "Foliage" is selected in category dropdown
    And Category filter is applied
    And Only plants from "Foliage" category are displayed
    And Plants from other categories are hidden
    And Category filter works correctly