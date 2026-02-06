Feature: Forbidden delete sale via API

  @nonadminapi
  Scenario: TC_API_SALES_20 - Verify non-Admin user is forbidden from deleting a sale
    Given Non-Admin user is authenticated for deleting sale
    When Non-Admin user sends DELETE request to "/api/sales/2"
    Then Response status code is 403
    And Error message indicates forbidden access
