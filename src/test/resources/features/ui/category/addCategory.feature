Feature: Add Category functionality

  Scenario: Verify that admin user can successfully add a new main category with valid data
    Given Admin is logged in
    And Admin is on the Category List page
    When Admin clicks Add Category button
    And Admin enters Category Name as "Indoor"
    And Admin leaves Parent Category selection empty
    And Admin clicks Save button
    Then Category "Indoor" is saved successfully
    And System displays a success message
    And System navigates back to the Category List page
    And Newly added "Indoor" category appears in the category list

  Scenario: Verify validation message when Admin adds category without name
    Given Admin is logged in
    And Admin is on the Category List page
    When Admin clicks Add Category button
    And Admin is on the Add Category page
    And Admin leaves Category Name field empty
    And Admin clicks Save button
    Then System displays validation message "Category name is required" below the Category Name field
    And Category is not created