Feature: Retrieve sales via API when none exist

  @adminapi
  Scenario: TC_API_SALES_13 - Verify Admin receives empty list when no sales exist
    Given Admin user is authenticated for Sales API
    When Admin sends GET /api/sales request
    Then Response status code is 200
    And Response body is an empty array
