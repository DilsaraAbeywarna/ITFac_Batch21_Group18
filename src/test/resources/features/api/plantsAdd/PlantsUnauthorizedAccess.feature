Feature: Plants API - Unauthorized Access Without Authentication

  @API_Plant_UnauthorizedAccess_010
  Scenario: Verify request without authentication token returns 401 Unauthorized
    Given No authentication token is provided
    When Unauthenticated user sends POST request to create plant "Orchid" under category 4 with price 90 and quantity 5
    Then Create API should return 401 Unauthorized status
    And Response should contain authentication error
    And Error message should indicate authentication is required
    And Error response should be properly formatted with 401 status
    And Plant "Orchid" should NOT be created due to missing authentication
