Feature: Plants api
   
    @adminapi
	Scenario: Verify Admin can retrieve plants summary successfully
		When Admin sends GET /api/plants/summary
		Then Plant summary response status code is 200
		And Plant summary Response body contains expected json format
