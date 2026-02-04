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
    Given Check the side menu is rendered with releveant menu texts
    When Admin hovers over the menu in the side menu
    Then The menu background color should change to "#374151"

@admin
Scenario: Scale up Sales Categories Inventory Plants Cards When Hovers On Each Card
    Given Admin logged in and is on the dashbord page
    Given Check the 04 card componenets are rendered 
    When Admin hovers over the card item in the dashboard
    Then The card shows a motion on y axis

@admin
Scenario: Display Add Button In The Category Page
   Given Admin logged in and is on the dashbord page
   Given check the side menu is rendered with releveant menu texts
   When  clicks the categories title on the sub menu  
   And naviagate to the categories page 
   Then Add a category button should visible
