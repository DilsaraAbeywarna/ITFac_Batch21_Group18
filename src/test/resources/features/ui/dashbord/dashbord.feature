Feature: Dashbord Page 

@admin
Scenario: Render Dashbord Page Successfully
    Given Admin logged in and is on the dashbord page
    Then Check the header is displayed with title "QA Training Application"    
    And Check the side menu is rendered with releveant menu texts
    And Check the 04 card componenets are rendered 


@admin
Scenario: Change Backgorund Color Of Menu Buttons When Hover
    Given Admin logged in and is on the dashbord page
    Then Check the side menu is rendered with releveant menu texts
    When Admin hovers over the menu in the side menu
    Then The menu background color should change to "#374151"

@admin
Scenario: Scale up Sales Categories Inventory Plants Cards When Hovers On Each Card
    Given Admin logged in and is on the dashbord page
    Given Check the 04 card componenets are rendered 
    When Admin hovers over the card item in the dashboard
    Then The card shows a motion on y axis


@admin
Scenario: Show Category Plants Sales Summary Information On Each Card
   Then Admin navigates to dashboard page
   Then Check the 04 card componenets are rendered 
   Then Admin should see 15 main categories on dashboard
   Then Admin should see 1 sub categories on dashboard
   And Admin should see 0 total plants on dashboard
   And Admin should see 0 low stock plants on dashboard
   And Admin should see revenue "Rs 0.0" on dashboard
   And Admin should see 0 sales count on dashboard