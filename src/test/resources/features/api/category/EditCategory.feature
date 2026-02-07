@adminapi
Feature: Edit category functionality

  @api-edit-category @api-edit-category-success
  Scenario: Verify Admin can update a main category via API
    Given A category exists in the system
    When Admin user updates the category name to "Outdoor"
    Then API should return 200 OK status for category update
    And Response body should contain updated category name "Outdoor"
    And Response body should contain the same category ID
    And Category should be updated successfully in the system

  @api-edit-category @api-edit-category-empty-name
  Scenario: Verify API rejects category update with empty name
    Given A category exists in the system
    When Admin user updates the category name to ""
    Then API should return 400 Bad Request status for invalid category update
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category details should remain unchanged in the system

  @api-edit-category @api-edit-category-short-name
  Scenario: Verify API rejects category update with name less than 3 characters
    Given A category exists in the system
    When Admin user updates the category name to "In"
    Then API should return 400 Bad Request status for invalid category update
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category details should remain unchanged in the system

  @api-edit-category @api-edit-category-long-name
  Scenario: Verify API rejects category update with name more than 10 characters
    Given A category exists in the system
    When Admin user updates the category name to "IndoorPlants"
    Then API should return 400 Bad Request status for invalid category update
    And Response body should contain validation error message
    And Response body should contain error type "BAD_REQUEST"
    And Category details should remain unchanged in the system