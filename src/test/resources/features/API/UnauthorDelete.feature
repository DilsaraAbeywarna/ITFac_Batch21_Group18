Feature: Unauthorized delete sale via API

  Scenario: TC_API_SALES_18 - Verify unauthenticated user cannot delete a sale
    When Unauthenticated user sends DELETE request to "/api/sales/2"
    Then Response status code is 401
    And Authentication error message is returned
