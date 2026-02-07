@nonadminapi
Feature: Plants API - Create Plant Forbidden for Normal User

  @API_Plant_CreateForbidden_009
  Scenario: Verify Normal User cannot create a plant (403 Forbidden)
    Given User is NOT an Admin
    When Normal User sends POST request to create plant "Daisy" under category 4 with price 40 and quantity 10
    Then Create API should return 403 Forbidden status
    And Response should contain authorization error
    And Error response should be properly formatted
    And Plant "Daisy" should NOT be created in the database
