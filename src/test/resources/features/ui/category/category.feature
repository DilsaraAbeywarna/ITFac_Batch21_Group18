Feature: Category Page

@admin
Scenario: Display Add Button In The Category Page
  Given Admin is logged in as admin
  When Admin navigates to the categories page
  Then Admin should see add a category button



@user
Scenario: Render Category Page Successfully
   Given User is logged in as user
   When User navigates to the categories page
   Then Sub header exits with Categories text
   And Text input component exists 
   And Select component is exiting with All Parents selected
   And Button exits with text Search
   And Button exits with text Reset
   And Table exits

@user
Scenario: Filter the categories by the category name
   Given Admin is logged in as admin
   When Admin navigates to the categories page
   Then Admin adds 15 categories to the system
   Given User is logged in as user
   When User navigates to the categories page
   Then User type the "Rose" category in the search bar
   Then User clicks the search button
   Then User should see Rose feild only
   Then User should see "Rose" field only
   Then User should see the search results
   Then User verifies "Rose" appears in results
   Then User clears the search
   Then User clicks reset button

@user
Scenario: Display list of categories with pagination
   Given Admin is logged in as admin
   When Admin navigates to the categories page
   Then Admin adds 15 categories to the system
   Given User is logged in as user
   When User navigates to the categories page
   Then User should see 10 records in the page
   And User should see next and previous buttons
   And User should see 2 page buttons
   And User clicks the next button
   And User should see 5 records in the page
   And User clicks the previous button

@user 
Scenario: Display "No Category Found" message when search for non exisiting category
   Given Admin is logged in as admin
   When Admin navigates to the categories page
   Then Admin adds 15 categories to the system
   Given User is logged in as user
   When User navigates to the categories page
   Then User should see 10 records in the page
   Then User type the "NonExistentCategory123XYZ" category in the search bar
   Then User clicks the search button
   Then User should see "No category found" message


@user
Scenario: Display categories sorted by category Id 
   Given Admin is logged in as admin
   When Admin navigates to the categories page
   Then Admin adds 15 categories to the system
   Given User is logged in as user
   When User navigates to the categories page
   Then User should see 10 records in the page
   Then User should see categories sorted by ID in descending order
   When User clicks on the "ID" column header
   Then User should see categories sorted by ID in ascending order
   When User clicks on the "ID" column header
   Then User should see categories sorted by ID in descending order

