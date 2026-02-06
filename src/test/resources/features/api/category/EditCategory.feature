@adminapi
Feature: Edit category functionality

  @api-category @edit-success
  Scenario: Verify Admin can update a main category via API
    Given A category exists in the system
    When Admin user updates the category name to "Outdoor"
    Then API should return 200 OK status for category update
    And Response body should contain updated category name "Outdoor"
    And Response body should contain the same category ID
    And Category should be updated successfully in the system