Feature: Create sale via API

  @adminapi
  Scenario: TC_API_SALES_15 - Verify Admin can successfully sell a plant with valid quantity
    Given Admin is authenticated for creating sales
    When Admin sends POST request to "/api/sales/plant/1" with quantity 20
    Then Response status code is 201
    And Sale record is created in response
    And Response contains sale id
    And Response contains correct plant details
    And Response contains sold quantity 20
    And Response contains totalPrice
    And Response contains soldAt timestamp
