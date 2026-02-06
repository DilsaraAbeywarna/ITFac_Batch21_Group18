Feature: Unauthorized access to sales API

  Scenario: TC_API_SALES_14 - Verify unauthenticated user cannot retrieve sales
    When Unauthenticated user sends GET request to sales API
    Then Response status code is 401
    And Authentication error message is returned
