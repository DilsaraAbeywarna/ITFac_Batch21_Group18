@nonadminapi
Feature: Plants API - Filter Plants by Category

  @API_Plant_FilterByCategory_008
  Scenario: Verify Normal User can filter plants by category successfully
    Given Category with ID 4 exists and contains plants
    When Normal User sends GET request to filter plants by category 4
    Then Filter API should return 200 OK status
    And Filter response should contain paginated structure
    And Response should contain only plants with category ID 4
    And Plants from other categories are excluded from results
    And API category filter works correctly