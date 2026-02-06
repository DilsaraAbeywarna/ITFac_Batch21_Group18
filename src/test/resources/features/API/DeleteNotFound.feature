Feature: Get sale by ID via API

  @adminapi
  Scenario: TC_API_SALES_19 - Verify API returns 404 when Sale ID does not exist
    Given Admin is authenticated for retrieving sale
    When Admin sends GET request to "/api/sales/999"
    Then Response status code is 404
    And Error message indicates sale not found
