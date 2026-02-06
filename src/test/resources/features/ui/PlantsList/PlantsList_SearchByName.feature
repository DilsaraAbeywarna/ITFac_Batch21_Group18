@UI_PlantList_SearchByName
Feature: Plant List Search by Name UI/UX

  @UI_PlantList_SearchByName_007
  Scenario: Verify Normal User can search plants by name
    Given Normal User is logged in
    And Multiple plants exist with different names
    And User is on plant list page for search
    Then Plant list page is displayed for search
    When User enters "Rose" in search box
    And User clicks Search button
    When User validates filtered results
    Then "Rose" is entered in search field
    And Search is executed
    And Only plants with "Rose" in name are displayed
    And Other plants are filtered out
    And Search results match query
    