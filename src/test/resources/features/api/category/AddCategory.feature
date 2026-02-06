@adminapi
Feature: Add category functionality

  @api-category @add-success
  Scenario: API_Category_CreateCategory_001 - Verify Admin can create main category via API with valid data
    When Admin user sends POST request to create main category with name "Indoor"
    Then API should return 201 Created status for category
    And Response body should contain category name "Indoor"
    And Response body should contain auto-generated category ID
    And Main category should be created successfully in the system


  @api-category @add-empty-name
  Scenario: API_Category_EmptyCategoryNameValidation_002 - Verify API validation when category name is empty
    When Admin user sends POST request to create main category with name ""
    Then API should return 400 Bad Request status
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system


  @api-category @add-invalid-length @min-length-validation
  Scenario: API_Category_CategoryNameLengthValidation_003 - Verify API validation when category name is less than 3 characters
    When Admin user sends POST request to create main category with name "In"
    Then API should return 400 Bad Request status
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system


  @api-category @add-invalid-length @max-length-validation
  Scenario: API_Category_CategoryNameLengthValidation_004 - Verify API validation when category name exceeds 10 characters
    When Admin user sends POST request to create main category with name "IndoorPlants"
    Then API should return 400 Bad Request status
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category should not be created in the system