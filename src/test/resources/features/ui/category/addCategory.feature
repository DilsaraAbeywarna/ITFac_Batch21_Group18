
Feature: Add Category functionality

  @add-category @add-category-success
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

  @add-category @add-category-empty-name
  Scenario: Verify validation message when Admin adds category without name
    Given Admin is logged in
    And Admin is on the Category List page
    When Admin clicks Add Category button
    And Admin is on the Add Category page
    And Admin clicks Save button
    Then System displays validation message "Category name is required" below the Category Name field
    And Category is not created

  @add-category @add-category-min-length
  Scenario: Verify category name length validation for less than minimum characters
    Given Admin is logged in
    And Admin is on the Category List page
    When Admin clicks Add Category button
    And Admin is on the Add Category page
    And Admin enters Category Name as "In"
    And Admin clicks Save button
    Then System displays validation message "Category name must be between 3 and 10 characters" below the Category Name field
    And Category is not created


  @add-category @add-category-cancel-navigation
  Scenario: Cancel_Add_Category_Should_Return_To_List_Without_Saving
    Given Admin is logged in
    And Admin is on the Category List page
    When Admin clicks Add Category button
    And Admin is on the Add Category page
    And Admin enters Category Name as "Outdoor"
    And Admin leaves Parent Category selection empty
    And Admin clicks Cancel button
    Then System navigates back to the Category List page
    And Category "Outdoor" is not saved
    And Category list remains unchanged