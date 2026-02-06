package starter.stepdefinitions.Api.categories;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.ResponseHolder;
import utils.TokenHolder;

public class CategoriesSteps {

    // Verify Admin can retrieve plants summary successfully
    @When("^Admin sends GET /api/categories/summary$")
    public void admin_sends_get_api_categories_summary_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/summary")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("categories", response);
    }

    @Then("Category summary response status code is {int}")
    public void category_summary_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("categories");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Category summary API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Category summary Response body contains expected json format")
    public void response_body_contains_expected_json_format() {
        Response response = ResponseHolder.getResponse("categories");
        System.out.println("🔍 Validating category summary response body format " + response);
        response.then()
                .assertThat()
                .body("$", org.hamcrest.Matchers.hasKey("mainCategories"))
                .body("$", org.hamcrest.Matchers.hasKey("subCategories"))
                .body("mainCategories", org.hamcrest.Matchers.instanceOf(Number.class))
                .body("subCategories", org.hamcrest.Matchers.instanceOf(Number.class))
                .body("mainCategories", org.hamcrest.Matchers.greaterThan(0))
                .body("subCategories", org.hamcrest.Matchers.greaterThanOrEqualTo(0));

        Map<String, Object> summary = response.jsonPath().getMap("$");
        Assertions.assertThat(summary).hasSize(2);

        System.out.println("✓ Response format validated: " + summary);
    }

    // Verify categories are filtered by the category name for admin users
    @When("^Admin sends GET /api/categories\\?name=Rose$")
    public void admin_sends_get_api_categories_name_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories?name=Rose")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("filteredCategory", response);
    }

    @Then("Category filtered response status code is {int}")
    public void filtered_category_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("filteredCategory");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Filtered category API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Retrieve the correct category type")
    public void retrieve_the_correct_category_type() {
        Response response = ResponseHolder.getResponse("filteredCategory");

        // 1. Verify response is an array
        List<Map<String, Object>> categories = response.jsonPath().getList("$");

        Assertions.assertThat(categories)
                .as("Categories list")
                .isNotNull()
                .isNotEmpty();

        // 2. Verify each category object has the required fields
        for (int i = 0; i < categories.size(); i++) {
            Map<String, Object> category = categories.get(i);

            // Verify required fields exist
            Assertions.assertThat(category)
                    .as("Category object " + (i + 1))
                    .containsKeys("id", "name", "parentName");

            // Verify field types
            Assertions.assertThat(category.get("id"))
                    .as("Category id should be a number")
                    .isInstanceOf(Number.class);

            Assertions.assertThat(category.get("name"))
                    .as("Category name should be a string")
                    .isInstanceOf(String.class);

            Assertions.assertThat(category.get("parentName"))
                    .as("Category parentName should be a string")
                    .isInstanceOf(String.class);

            // Verify the name contains "Rose" (case-insensitive)
            String categoryName = (String) category.get("name");
            Assertions.assertThat(categoryName.toLowerCase())
                    .as("Category name should contain 'Rose'")
                    .contains("rose");
        }

    }

    // Verify selected category is not deleted for a invalid category id for admin
    @When("Admin sends invalid category id Delete \\/api\\/categories\\/{int}")
    public void admin_sends_delete_api_invalid_categories_request(int categoryId) {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/categories/" + categoryId)
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("deleteInValidCategory", response);

    }

    @Then("Category delete Invalid response status code is {int}")
    public void category_delete_invalid_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("deleteInValidCategory");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Delete InvalidCategory API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Retrieve Invalid category id deletion message")
    public void retrieve_invalid_category_id_deletion_message() {
        Response response = ResponseHolder.getResponse("deleteInValidCategory");

        System.out.println("🔍 Validating error response format");
        System.out.println("📋 Response body: " + response.getBody().asString());

        // 1. Verify response has all expected error fields
        response.then()
                .assertThat()
                .body("$", org.hamcrest.Matchers.hasKey("status"))
                .body("$", org.hamcrest.Matchers.hasKey("error"))
                .body("$", org.hamcrest.Matchers.hasKey("message"))
                .body("$", org.hamcrest.Matchers.hasKey("timestamp"));

        // 2. Verify field values and types
        int status = response.jsonPath().getInt("status");
        String error = response.jsonPath().getString("error");
        String message = response.jsonPath().getString("message");
        String timestamp = response.jsonPath().getString("timestamp");

        // 3. Validate status field
        Assertions.assertThat(status)
                .as("Status code in response body")
                .isEqualTo(404);

        // 4. Validate error field
        Assertions.assertThat(error)
                .as("Error type")
                .isNotNull()
                .isEqualTo("NOT_FOUND");

        // 5. Validate message field
        Assertions.assertThat(message)
                .as("Error message for invalid category deletion")
                .isNotNull()
                .isNotEmpty()
                .containsIgnoringCase("Category not found");

        // 6. Validate timestamp field
        Assertions.assertThat(timestamp)
                .as("Timestamp should not be null")
                .isNotNull()
                .isNotEmpty();

    }

    // Verify selected category is deleted for a valid category id for admin users
    @When("^Admin sends valid category id Delete /api/categories/id$")
    public void admin_sends_delete_api_valid_categories_request() {
        String token = TokenHolder.getToken();

        Response postResponse = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body("{ \"name\": \"test\" }")
                .when()
                .post("/api/categories")
                .then()
                .extract()
                .response();

        Assertions.assertThat(postResponse.statusCode())
                .as("Category creation should succeed")
                .isEqualTo(201);
        int createdCategoryId = postResponse.jsonPath().getInt("id");

        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/categories/" + createdCategoryId)
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("deleteValidCategory", response);
    }

    @Then("Category delete valid response status code is {int}")
    public void category_delete_valid_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("deleteValidCategory");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Delete InvalidCategory API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    // Verify the categories are retrived with pagination for users
    @When("User sends GET \\/api\\/categories\\/page?page=0&size=12&sortField=id&sortDir=asc")
    public void admin_sends_get_api_categories_page_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/page?page=0&size=12&sortField=id&sortDir=asc")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("paginatedCategories", response);
    }

    @Then("Category paginated response status code is {int}")
    public void category_paginated_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("paginatedCategories");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Paginated categories API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Retrive paginated category list with correct length and response")
    public void retrieve_paginated_category_list_with_correct_length_and_response() {
        Response response = ResponseHolder.getResponse("paginatedCategories");

        System.out.println("🔍 Validating paginated category response");

        // Validate pagination structure using Hamcrest
        response.then()
                .assertThat()
                .body("content", org.hamcrest.Matchers.notNullValue())
                .body("content", org.hamcrest.Matchers.instanceOf(List.class))
                .body("content.size()", org.hamcrest.Matchers.greaterThan(0))
                .body("totalElements", org.hamcrest.Matchers.greaterThan(0))
                .body("totalPages", org.hamcrest.Matchers.greaterThan(0))
                .body("numberOfElements", org.hamcrest.Matchers.greaterThan(0))
                .body("empty", org.hamcrest.Matchers.equalTo(false))
                // Validate first category structure
                .body("content[0]", org.hamcrest.Matchers.hasKey("id"))
                .body("content[0]", org.hamcrest.Matchers.hasKey("name"))
                .body("content[0]", org.hamcrest.Matchers.hasKey("parentName"))
                .body("content[0].id", org.hamcrest.Matchers.instanceOf(Number.class))
                .body("content[0].name", org.hamcrest.Matchers.instanceOf(String.class))
                .body("content[0].parentName", org.hamcrest.Matchers.instanceOf(String.class));

        // Extract and validate content length
        List<Map<String, Object>> content = response.jsonPath().getList("content");

        Assertions.assertThat(content.size())
                .as("Content size should match numberOfElements")
                .isEqualTo(12);

        System.out.println("✓ Paginated response validated: " + content.size() + " categories in page");
    }

    // Verify the relevant categories are sorted according to the id for users
    @When("User sends GET \\/api\\/categories\\/page?sortField=id&sortDir=asc")
    public void user_sends_get_api_categories_sorted_by_id_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/page?sortField=id&sortDir=asc")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("sortedCategories", response);
    }

    @Then("Category sorted response status code is {int}")
    public void category_sorted_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("sortedCategories");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Sorted categories API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Retrive sorted category list")
    public void retrieve_sorted_category_list_with_correct_order() {
        Response response = ResponseHolder.getResponse("sortedCategories");

        response.then()
                .assertThat()
                .body("content", org.hamcrest.Matchers.notNullValue())
                .body("content", org.hamcrest.Matchers.instanceOf(List.class))
                .body("content.size()", org.hamcrest.Matchers.greaterThan(0))
                .body("sort.sorted", org.hamcrest.Matchers.equalTo(true))
                .body("sort.unsorted", org.hamcrest.Matchers.equalTo(false));

        // 2. Extract categories list
        List<Map<String, Object>> categories = response.jsonPath().getList("content");

        Assertions.assertThat(categories)
                .as("Categories list should not be empty")
                .isNotEmpty();

        List<Integer> categoryIds = new java.util.ArrayList<>();
        for (Map<String, Object> category : categories) {
            Integer id = (Integer) category.get("id");
            categoryIds.add(id);
            System.out.println("  ├─ Category ID: " + id + ", Name: " + category.get("name"));
        }

        List<Integer> sortedIds = new java.util.ArrayList<>(categoryIds);
        java.util.Collections.sort(sortedIds);


        Assertions.assertThat(categoryIds)
                .as("Category IDs should be sorted in descending order")
                .isEqualTo(sortedIds);

        Map<String, Object> sort = response.jsonPath().getMap("sort");
        Assertions.assertThat(sort.get("sorted"))
                .as("Sort.sorted should be true")
                .isEqualTo(true);
        Assertions.assertThat(sort.get("unsorted"))
                .as("Sort.unsorted should be false")
                .isEqualTo(false);
    }

    // Verify the categories are sorted according to name for users
    @When("User sends GET \\/api\\/categories\\/page?sortField=name&sortDir=asc")
    public void user_sends_get_api_categories_sorted_by_name_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/page?sortField=name&sortDir=asc")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("sortedCategoriesByName", response);
    }

    @Then("Category names sorted response status code is {int}")
    public void category_names_sorted_response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("sortedCategoriesByName");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Sorted categories by name API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Retrive names sorted category list")
    public void retrieve_names_sorted_category_list() {
        Response response = ResponseHolder.getResponse("sortedCategoriesByName");

        response.then()
                .assertThat()
                .body("content", org.hamcrest.Matchers.notNullValue())
                .body("content", org.hamcrest.Matchers.instanceOf(List.class))
                .body("content.size()", org.hamcrest.Matchers.greaterThan(0))
                .body("sort.sorted", org.hamcrest.Matchers.equalTo(true))
                .body("sort.unsorted", org.hamcrest.Matchers.equalTo(false));

        List<Map<String, Object>> categories = response.jsonPath().getList("content");

        Assertions.assertThat(categories)
                .as("Categories list should not be empty")
                .isNotEmpty();

        List<String> categoryNames = new java.util.ArrayList<>();
        for (Map<String, Object> category : categories) {
            String name = (String) category.get("name");
            categoryNames.add(name);
            System.out.println("  ├─ Category Name: " + name + ", ID: " + category.get("id"));
        }

        List<String> sortedNames = new java.util.ArrayList<>(categoryNames);
        sortedNames.sort(String.CASE_INSENSITIVE_ORDER);

        Assertions.assertThat(categoryNames)
                .as("Category names should be sorted alphabetically")
                .isEqualTo(sortedNames);

        Map<String, Object> sort = response.jsonPath().getMap("sort");
        Assertions.assertThat(sort.get("sorted"))
                .as("Sort.sorted should be true")
                .isEqualTo(true);
        Assertions.assertThat(sort.get("unsorted"))
                .as("Sort.unsorted should be false")
                .isEqualTo(false);
    }

    // Verify selected category is not deleted for a valid category id for users
    @When("^User sends valid category id Delete /api/categories/id$")
    public void user_sends_delete_api_valid_categories_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/categories/149")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("userDeleteCategory", response);
    }

    @Then("User gets unauthorized response code {int}")
    public void user_gets_unauthorized_response_code(int statusCode) {
        Response response = ResponseHolder.getResponse("userDeleteCategory");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("User delete category API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    // Verify the relevant sub categories are retrived for the parent id for users
    @When("User sends GET \\/api\\/categories\\/page?parentId=137")
    public void user_sends_get_api_categories_by_parent_id_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/page?parentId=137")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("categoriesByParentId", response);
    }

    @Then("User gets sub categories for the provided parent id")
    public void user_gets_sub_categories_for_the_provided_parent_id() {
        Response response = ResponseHolder.getResponse("categoriesByParentId");

        // 1. Validate response has pagination structure
        response.then()
                .assertThat()
                .body("content", org.hamcrest.Matchers.notNullValue())
                .body("content", org.hamcrest.Matchers.instanceOf(List.class))
                .body("totalElements", org.hamcrest.Matchers.greaterThan(0))
                .body("totalPages", org.hamcrest.Matchers.greaterThan(0))
                .body("empty", org.hamcrest.Matchers.equalTo(false));

        // 2. Extract categories list
        List<Map<String, Object>> categories = response.jsonPath().getList("content");

        Assertions.assertThat(categories)
                .as("Sub-categories list should not be empty")
                .isNotEmpty();


        // 3. Validate each category has required fields and structure
        for (int i = 0; i < categories.size(); i++) {
            Map<String, Object> category = categories.get(i);

            System.out.println("🔍 Validating sub-category " + (i + 1) + ": " + category);

            // Verify required fields exist
            Assertions.assertThat(category)
                    .as("Sub-category object " + (i + 1) + " should have required fields")
                    .containsKeys("id", "name", "parentName");

            // Verify field types
            Assertions.assertThat(category.get("id"))
                    .as("Sub-category id should be a number")
                    .isInstanceOf(Number.class);

            Assertions.assertThat(category.get("name"))
                    .as("Sub-category name should be a string")
                    .isInstanceOf(String.class)
                    .as("Sub-category name should not be empty")
                    .asString()
                    .isNotEmpty();

            Assertions.assertThat(category.get("parentName"))
                    .as("Sub-category parentName should be a string")
                    .isInstanceOf(String.class)
                    .as("Sub-category parentName should not be empty or '-'")
                    .asString()
                    .isNotEmpty()
                    .isNotEqualTo("-");

            // Get the actual values
            Integer id = (Integer) category.get("id");
            String name = (String) category.get("name");
            String parentName = (String) category.get("parentName");

            System.out.println("  ├─ ID: " + id);
            System.out.println("  ├─ Name: " + name);
            System.out.println("  └─ Parent Name: " + parentName);

            // 4. Additional validation: Verify this is actually a sub-category
            // (has a parent, so parentName should NOT be "-")
            Assertions.assertThat(parentName)
                    .as("Sub-category should have a parent (parentName should not be '-')")
                    .isNotEqualTo("-");
        }

        // 5. Verify all sub-categories belong to the same parent (if multiple)
        if (categories.size() > 1) {
            String firstParentName = (String) categories.get(0).get("parentName");

            for (Map<String, Object> category : categories) {
                String parentName = (String) category.get("parentName");
                Assertions.assertThat(parentName)
                        .as("All sub-categories should have the same parent")
                        .isEqualTo(firstParentName);
            }

        }

        // 6. Verify pagination metadata
        int numberOfElements = response.jsonPath().getInt("numberOfElements");

        Assertions.assertThat(numberOfElements)
                .as("Number of elements should match content size")
                .isEqualTo(categories.size());

        System.out.println("✓ [SUB-CATEGORIES] Validated " + categories.size() + " sub-categories successfully");
    }
}
