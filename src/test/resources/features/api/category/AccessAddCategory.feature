@nonadminapi 
Feature: Add category access control

  @api-add-unauthorized @api-add-unauthorized-create
  Scenario: Verify non-admin user cannot create category via API
    When Non-Admin user sends POST request to create main category with name "Outdoor"
    Then API should return 403 Forbidden status for category creation
    And Response body should contain error type "Forbidden"
    And Category should not be created in the system

  @api-add-unauthorized @api-add-unauthorized-validation-empty-name
  Scenario: Verify non-admin user cannot create category with invalid data via API
    When Non-Admin user sends POST request to create main category with name ""
    Then API should return 403 Forbidden status for category creation
    And Response body should contain error type "Forbidden"
    And Category should not be created in the system
    And Validation rules should not be applied due to lack of permission

  @api-add-unauthorized @api-add-unauthorized-validation-short-name
  Scenario: Verify non-admin user cannot create category with invalid data via API
    When Non-Admin user sends POST request to create main category with name "In"
    Then API should return 403 Forbidden status for category creation
    And Response body should contain error type "Forbidden"
    And Category should not be created in the system
    And Validation rules should not be applied due to lack of permission

  @api-add-unauthorized @api-add-unauthorized-validation-long-name
  Scenario: Verify non-admin user cannot create category with invalid data via API
    When Non-Admin user sends POST request to create main category with name "IndoorPlants"
    Then API should return 403 Forbidden status for category creation
    And Response body should contain error type "Forbidden"
    And Category should not be created in the system
    And Validation rules should not be applied due to lack of permission