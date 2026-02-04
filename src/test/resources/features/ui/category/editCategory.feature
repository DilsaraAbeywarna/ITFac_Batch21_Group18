Feature: Edit Category functionality

  @edit-category
  Scenario: Verify Admin can successfully edit an existing category with valid data
    Given Admin is logged in
    And Admin is on the Category List page
    And Admin ensures test category "Indoor" exists
    When Admin clicks Edit icon for existing category "Indoor"
    Then Edit Category page for "Indoor" category is displayed
    And Admin updates Category Name to "Outdoor"
    And Admin clicks Save button
    Then Category details are updated successfully
    And System displays a success message
    And System navigates back to the Category List page
    And Updated category "Outdoor" appears in the category list