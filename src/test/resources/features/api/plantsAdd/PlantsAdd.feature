@adminapi
Feature: Plants API - Add Plant
  As an Admin user
  I want to create new plants via API
  So that the plant inventory can be managed efficiently

  # Test Identifier: API_Plant_CreateValid_001
  # Test Summary: Verify successful plant creation by an Admin with valid data
  # Test Description: Ensure that the system accepts a valid request from an Admin and creates the plant resource
  # Precondition:
  #   1. The API is running
  #   2. A valid JWT token is generated for a user with the ADMIN role (via @adminapi hook)
  #   3. A plant with valid data is prepared (name, categoryId, price, quantity)
  #   4. At least one sub-category exists in the database
  # Expected Result:
  #   - Response status code is 201 Created
  #   - Plant name is "Rose_<timestamp>", categoryId is 3, price is 75.50, quantity is 20
  #   - Database reflects the new entry with all correct values
  @API_Plant_CreateValid_001
  Scenario: Verify successful plant creation by Admin with valid data
    When Admin user sends POST request to create plant under sub-category
    Then API should return 201 Created status
    And Response body should contain plant details with correct values

  # Test Identifier: API_Plant_MissingNameError_002
  # Test Summary: Verify validation error when plant name is missing
  # Test Description: Ensure the API rejects requests with missing required field (name) and returns appropriate error
  # Precondition:
  #   1. The API is running
  #   2. A valid JWT token is generated for a user with the ADMIN role (via @adminapi hook)
  # Expected Result:
  #   - Response status code is 400 Bad Request
  #   - Error message is "Plant name is required"
  #   - Plant is NOT created in the database
  @API_Plant_MissingNameError_002
  Scenario: Verify validation error when plant name is missing
    When Admin user sends POST request without plant name
    Then API should return 400 Bad Request status
    And Response body should contain validation error for missing name
    And Plant should not be created in database

  # Test Identifier: API_Plant_NameLengthError_003
  # Test Summary: Verify validation error for plant name length less than 3 characters
  # Test Description: Ensure the API validates plant name length and rejects names shorter than 3 characters
  # Precondition:
  #   1. The API is running
  #   2. A valid JWT token is generated for a user with the ADMIN role (via @adminapi hook)
  # Expected Result:
  #   - Response status code is 400 Bad Request
  #   - Error message is "Plant name must be between 3 and 25 characters"
  #   - Plant is NOT created in the database
  @API_Plant_NameLengthError_003
  Scenario: Verify validation error for plant name length less than 3 characters
    When Admin user sends POST request with plant name less than 3 characters
    Then API should return 400 Bad Request status
    And Response body should contain validation error for name length
    And Plant should not be created in database