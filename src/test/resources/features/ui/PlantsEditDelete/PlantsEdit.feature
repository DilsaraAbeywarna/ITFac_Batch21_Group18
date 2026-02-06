Feature: Plant Edit

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
    And Admin user selects the "0" category option
    And Admin user clicks the Save button
    Then Admin user should see the category validation error message

  @admin
  Scenario: UI_PLANTEDIT_SAVEBUTTON_06 - Verify Save button works with valid plant edit data
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user enters a Quantity "150"
    And Admin user enters a Plant name "TestPlant"
    And Admin user enters a price "75.50"
    And Admin user selects the "2" category option
    And Admin user clicks the Save button
    Then Plant details are saved successfully and user is redirected to Plant List page

  @admin
  Scenario: UI_PLANTEDIT_CANCELBUTTON_07 - Verify Cancel button navigation from Plant page
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user modifies the plant name field
    And Admin user clicks the Cancel button
    Then Admin user is redirected to plants page

  @testuser
  Scenario: UI_PLANTEDIT_VIEWEDITEDLIST_10 - TestUser can view edited plant list
    Given Test User is logged in and has navigated to the Plant page
    When Test User navigates to the Plant List page
    Then edited Plant list displayed

  @testuser
  Scenario: UI_PLANTEDIT_LOWBADGE_11 - Verify "Low" badge display for plant quantity below 5 for test user role
    Given Test User is logged in and has navigated to the Plant page
    When Test User navigates to the Plant List page
    Then the plant should be listed with a "Low" badge visible near the quantity of stock column

  @testuser
  Scenario: UI_PLANTEDIT_EDITBUTTON_12 - Verify Edit button visibility for user test role
    Given Test User is logged in and has navigated to the Plant page
    When Test User navigates to the Plant List page
    Then Edit button is not displayed for the Test User role

  @admin 
  Scenario: UI_PLANTEDIT_CATEGORY_08 - Verify edit page Category selection
    Given Admin user is logged in and on the dashboard
    When Admin user navigates to the Plant List page
    And Admin user clicks the Edit button of a plant
    And Admin user notes the current category
    And Admin user selects a different valid sub-category "category01"
    And Admin user clicks the Save button
    Then Admin user is redirected to plants page
    And the category changes are not saved and the original category is displayed