@adminapi
Feature: Plants API - Get Plants Paginated

  @API_Plant_GetPaginatedList_005
  Scenario: Verify Admin can retrieve paginated plant list with proper metadata
    Given Database contains at least 15 plants
    When Admin user sends GET request to retrieve paginated plants with page 1 and size 10
    Then API should return 200 OK status
    And Response body should contain content array with plant objects
    And Each plant object should have required fields
    And Response body should contain pagination metadata