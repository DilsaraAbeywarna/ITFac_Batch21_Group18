@adminapi
Feature: Add category functionality

  @api-add-category  @api-add-category-success
  Scenario: Verify Admin can create main category via API with valid data
    When Admin user sends POST request to create main category with name "Indoor"
    Then API should return 201 Created status for category
    And Response body should contain category name "Indoor"
    And Response body should contain auto-generated category ID
    And Main category should be created successfully in the system

  @api-add-category  @api-add-category-empty-name
  Scenario: Verify API rejects category creation with empty name
    When Admin user sends POST request to create main category with name ""
    Then API should return 400 Bad Request status for category creation
    And Response body should contain validation error message for category creation
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system

  @api-add-category  @api-add-category-short-name
  Scenario: Verify API rejects category creation with name less than 3 characters
    When Admin user sends POST request to create main category with name "In"
    Then API should return 400 Bad Request status for category creation
    And Response body should contain validation error message for category creation
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system

  @api-add-category  @api-add-category-long-name
  Scenario: Verify API rejects category creation with name more than 10 characters
    When Admin user sends POST request to create main category with name "IndoorPlants"
    Then API should return 400 Bad Request status for category creation
    And Response body should contain validation error message for category creation
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system