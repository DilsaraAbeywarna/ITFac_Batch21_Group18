@nonadminapi
@api-edit-category 
Feature: Edit Category access control

  @api-edit-unauthorized @api-edit-unauthorized-update
  Scenario: Verify non-admin user cannot update category via API
    Given A category exists in the system
    When Non-Admin user updates the category name to "Outdoor"
    Then API should return 403 Forbidden status for category update
    And Response body should contain error type "Forbidden"
    And Category details should remain unchanged in the system