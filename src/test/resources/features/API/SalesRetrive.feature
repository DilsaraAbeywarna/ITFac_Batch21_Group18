
Feature: Retrieve sales via API
   
    @adminapi
	Scenario: Verify Admin can retrieve all sales successfully
		When Admin sends GET /api/sales request
		Then Response status code is 200
		And Response body contains a list of sales
		And Each sale object includes required fields
