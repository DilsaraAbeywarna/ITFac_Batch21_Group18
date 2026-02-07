@nonadminapi
@api-delete-category
Feature: Delete Category access control

  @api-delete-unauthorized 
  Scenario: Verify non-admin user cannot delete category via API
    Given A category exists in the system for deletion
    When Non-Admin user sends DELETE request to delete the category
    Then API should return 403 Forbidden status for category deletion
    And Response body should contain error type "Forbidden"
    And Category should not be deleted from the system