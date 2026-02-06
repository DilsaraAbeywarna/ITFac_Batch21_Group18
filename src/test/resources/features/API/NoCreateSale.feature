Feature: Failed sale creation via API

  @adminapi
  Scenario: TC_API_SALES_16 - Verify inventory is not updated when selling with invalid quantity
    Given Admin is authenticated for selling plants
    When Admin sends POST request to "/api/sales/plant/2" with invalid quantity 1000
    Then Response status code is 400
    And Response contains error status
    And Response contains error message indicating invalid quantity
    And Response contains timestamp
