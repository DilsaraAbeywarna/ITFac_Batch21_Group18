Feature: Plant Add Page UI/UX

  # Test Case 5: Low Badge Display (UI/UX)
  Scenario: Verify Low badge displays when quantity < 5
    Given Admin is logged in
    And Admin is on plant list page
    When Admin navigates to "/ui/plants"
    Then Plant list page should be displayed
    When Admin locates plant with quantity = 3
    And Admin observes plant row
    Then Plant row is visible
    And "Low" badge is displayed next to the plant
    And Badge indicates low stock status

  # Test Case 4: Price validation (must be > 0)
  Scenario: Price zero shows validation error
    Given Admin is logged in
    And Admin is on Add Plant page
    When Admin enters plant name "Tulip"
    And Admin selects a sub-category from dropdown
    And Admin enters price "0"
    And Admin enters quantity "10"
    And Admin clicks the Save button
    Then Error message "Price must be greater than 0" is displayed below price field in red
    And Plant is not created

  # Test Case 1: Add Plant Button Visibility
  Scenario: Admin sees Add Plant button on plant list page
    Given Admin is logged in
    And Admin is on plant list page
    Then Plant list page should be displayed
    When Admin observes the "Add Plant" button
    Then "Add Plant" button should be visible
    And "Add Plant" button should be enabled and clickable

  # Test Case 2: Verify Admin can add plant with valid data
  Scenario: Admin can add plant with valid data
    Given Admin is logged in
    And At least one sub-category exists
    And Admin is on Add Plant page
    When Admin enters plant name "Rose"
    And Admin selects a sub-category from dropdown
    And Admin enters price "50"
    And Admin enters quantity "10"
    And Admin clicks the Save button
    Then System displays success message
    And User is redirected to plant list page
    And New plant "Rose" appears in the list

  # Test Case 3: Plant name length validation (3-25 chars)
  Scenario: Plant name too short shows validation error
    Given Admin is logged in
    And Admin is on Add Plant page
    When Admin enters plant name "AB"
    And Admin selects a sub-category from dropdown
    And Admin enters price "50"
    And Admin enters quantity "10"
    And Admin clicks the Save button
    Then Error message "Plant name must be between 3 and 25 characters" is displayed below name field in red
    And Plant is not created