@api @plant
Feature: Plant Edit API
  As an Admin user
  I want to update plant details via API
  So that I can maintain accurate plant information in the system

  Background:
    Given the Plant API base URL is "http://localhost:8080"

  @API_PLANT_EDIT_01
  Scenario: API_PLANT_EDIT_01 - Update plant with valid data
    Given Admin is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Admin sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "Anthurium",
        "price": 150,
        "quantity": 25,
        "categoryId": 4
      }
      """
    Then the response status code should be 200
    And the response body should contain:
      | name     | Anthurium |
      | price    | 150.0     |
      | quantity | 25        |

  @API_PLANT_EDIT_02
  Scenario: API_PLANT_EDIT_02 - Update plant with empty name
    Given Admin is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Admin sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "",
        "price": 150,
        "quantity": 25,
        "categoryId": 4
      }
      """
    Then the response status code should be 400

  @API_PLANT_EDIT_03
  Scenario: API_PLANT_EDIT_03 - Update plant with negative quantity
    Given Admin is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Admin sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "Test Plant",
        "price": 150,
        "quantity": -10,
        "categoryId": 4
      }
      """
    Then the response status code should be 400

  @API_PLANT_EDIT_06
  Scenario: API_PLANT_EDIT_06 - Update plant using valid data as testuser
    Given Test User is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Test User sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "Updated Plant",
        "price": 200,
        "quantity": 50,
        "categoryId": 4
      }
      """
    Then the response status code should be 403

  @API_PLANT_EDIT_08
  Scenario: API_PLANT_EDIT_08 - Update plant with empty name as testuser
    Given Test User is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Test User sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "",
        "price": 150,
        "quantity": 25,
        "categoryId": 4
      }
      """
    Then the response status code should be 400

  @API_PLANT_EDIT_10
  Scenario: API_PLANT_EDIT_10 - Update plant with negative price as testuser
    Given Test User is authenticated with valid Bearer Token
    And a plant with id 13 exists in the system
    When Test User sends a PUT request to "/api/plants/13" with request body:
      """
      {
        "id": 13,
        "name": "Test Plant",
        "price": -100,
        "quantity": 25,
        "categoryId": 4
      }
      """
    Then the response status code should be 400

  @API_PLANT_DELETE_04
  Scenario: API_PLANT_DELETE_04 - Delete existing plant
    Given Admin is authenticated with valid Bearer Token
    When Admin sends a DELETE request to "/api/plants/22"
    Then the response status code should be 204

  @API_PLANT_DELETE_05
  Scenario: API_PLANT_DELETE_05 - Delete non-existing plant
    Given Admin is authenticated with valid Bearer Token
    When Admin sends a DELETE request to "/api/plants/99999"
    Then the response status code should be 204

  @API_PLANT_DELETE_07
  Scenario: API_PLANT_DELETE_07 - Delete Plant using valid ID
    Given Test User is authenticated with valid Bearer Token
    When Test User sends a DELETE request to "/api/plants/15"
    Then the response status code should be 403

  @API_PLANT_DELETE_09
  Scenario: API_PLANT_DELETE_09 - Delete Plant using invalid ID
    Given Test User is authenticated with valid Bearer Token
    When Test User sends a DELETE request to "/api/plants/99999"
    Then the response status code should be 403
