Feature: User retrieve sales via API

  @nonadminapi
  Scenario: TC_API_SALES_17 - Verify user can retrieve all sales successfully
    Given User is authenticated for viewing sales
    When User sends GET request to sales API
    Then Response status code is 200
    And Response body contains a list of sales
    And Each sale object includes required fields
