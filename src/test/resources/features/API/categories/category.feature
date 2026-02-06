Feature: Category api

@adminapi
Scenario: Verify Admin can retrieve plants summary successfully
	When Admin sends GET /api/categories/summary
	Then Category summary response status code is 200
	And Category summary Response body contains expected json format

@adminapi
Scenario: Verify categories are filtered by the category name for admin users
   When Admin sends GET /api/categories?name=Rose
   Then Category filtered response status code is 200
   And Retrieve the correct category type 

@adminapi
Scenario: Verify selected category is not deleted for a invalid category id for admin users
   When Admin sends invalid category id Delete /api/categories/890
   Then Category delete Invalid response status code is 404
   Then Retrieve Invalid category id deletion message


@adminapi
Scenario: Verify selected category is deleted for a valid category id for admin users
   When Admin sends valid category id Delete /api/categories/id
   Then Category delete valid response status code is 204


@nonadminapi
Scenario: Verify the categories are retrived with pagination for users
   When User sends GET /api/categories/page?page=0&size=12&sortField=id&sortDir=asc
   Then Category paginated response status code is 200
   Then Retrive paginated category list with correct length and response

@nonadminapi
Scenario: Verify the relevant categories are sorted according to the id for users
    When User sends GET /api/categories/page?sortField=id&sortDir=asc
    Then Category sorted response status code is 200
    Then Retrive sorted category list   

@nonadminapi
Scenario: Verify the categories are sorted according to name for users
   When User sends GET /api/categories/page?sortField=name&sortDir=asc
   Then Category names sorted response status code is 200
   Then Retrive names sorted category list 

@nonadminapi
Scenario: Verify selected category is not deleted for a valid category id for users
    When User sends valid category id Delete /api/categories/id
    Then User gets unauthorized response code 403


@nonadminapi
Scenario: Verify the relevant sub categories are retrived for the parent id for users
    When User sends GET /api/categories/page?parentId=137
    Then User gets sub categories for the provided parent id