@nonadminapi
Feature: Plants API - Get Plants as Normal User

  @API_Plant_GetList_006
  Scenario: Verify Normal User can retrieve all plants successfully
    Given Database contains multiple plants
    When Normal User sends GET request to retrieve all plants
    Then Normal user API should return 200 OK status
    And Response body should contain array of plant objects
    And Each plant object should have all required fields