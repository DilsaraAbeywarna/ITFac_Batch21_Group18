Feature: Delete sale successfully via API

  @adminapi
  Scenario: TC_API_SALES_21 - Verify deleting a sale
    Given Admin is authenticated for deleting sale
    When Admin sends DELETE request to "/api/sales/46"
    Then Response status code is 204
