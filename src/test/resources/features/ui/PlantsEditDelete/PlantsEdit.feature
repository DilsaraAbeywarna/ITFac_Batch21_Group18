Feature: Plant Edit Navigation

  @admin
  Scenario: UI_PLANTEDIT_NAVIGATION_01 - Navigate to Edit Plant page
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    Then Admin user should be navigated to the Edit Plant page

  @admin
  Scenario: UI_PLANTEDIT_NAMEVALIDATION_02 - Verify edit page Plant Name field validation
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user clears the plant name field
    And Admin user selects a valid sub-category
    And Admin user enters "50" in the price field
    And Admin user enters "100" in the quantity field
    And Admin user clicks the Save button
    Then Admin user should see the plant name validation error message

  @admin
  Scenario: UI_PLANTEDIT_PRICEVALIDATION_03 - Verify edit page Price field validation
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user selects a valid sub-category
    And Admin user enters a valid plant name
    And Admin user enters "100" in the quantity field
    And Admin user clears the price field
    And Admin user clicks the Save button
    Then Admin user should see the price validation error message

  @admin
  Scenario: UI_PLANTEDIT_QUANTITYVALIDATION_04 - Verify edit page Quantity field validation
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user selects a valid sub-category
    And Admin user enters a valid plant name
    And Admin user clears the quantity field
    And Admin user enters "50" in the price field
    And Admin user clicks the Save button
    Then Admin user should see the quantity validation error message
  @admin
  Scenario: UI_PLANTEDIT_CATEGORYVALIDATION_05 - Verify edit page Category selection validation
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user enters "100" in the quantity field
    And Admin user enters a valid plant name
    And Admin user enters "50" in the price field
    And Admin user selects the default category option
    And Admin user clicks the Save button
    Then Admin user should see the category validation error message