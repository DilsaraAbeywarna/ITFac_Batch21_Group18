@adminapi
Feature: Plants API - Add Plant

  @API_Plant_CreateValid_001
  Scenario: Verify successful plant creation by Admin with valid data
    When Admin user sends POST request to create plant under sub-category
    Then API should return 201 Created status
    And Response body should contain plant details with correct values

  @API_Plant_MissingNameError_002
  Scenario: Verify validation error when plant name is missing
    When Admin user sends POST request without plant name
    Then API should return 400 Bad Request status
    And Response body should contain validation error for missing name
    And Plant should not be created in database

  @API_Plant_NameLengthError_003
  Scenario: Verify validation error for plant name length less than 3 characters
    When Admin user sends POST request with plant name less than 3 characters
    Then API should return 400 Bad Request status
    And Response body should contain validation error for name length
    And Plant should not be created in database

  @API_Plant_MainCategoryError_004
  Scenario: Verify error when category is main category (not sub-category)
    When Admin user sends POST request with main category ID
    Then API should return 400 Bad Request status
    And Response body should contain error for main category
    And Plant should not be created in database