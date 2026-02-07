Feature: Failed sale creation with invalid plant ID via API

  @adminapi
  Scenario: TC_API_SALES_22 - Verify that inventory is not updated when selling with an not existing Plant ID
    Given Admin is authenticated for selling plants with invalid ID
    When Admin sends POST request to "/api/sales/plant/200" with quantity 10
    Then Response status code is 404
    And Error message indicates plant not found
