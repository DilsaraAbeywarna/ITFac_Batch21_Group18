@nonadminapi
Feature: Plants API - Search Plants by Name

  @API_Plant_SearchByName_007
  Scenario: Verify Normal User can search plants by name successfully
    Given Plant with name containing "Rose" exists in the database
    When Normal User sends GET request to search plants by name "Rose"
    Then Search API should return 200 OK status
    And Search response should contain paginated structure
    And Response should contain only plants with "Rose" in the name
    And Plants without "Rose" are excluded from results
    And Search should be case-insensitive