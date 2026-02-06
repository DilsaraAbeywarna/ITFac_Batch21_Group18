package starter.stepdefinitions.api.category;

import starter.api.category.AddEditCategoriesPageAPI;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;

import static org.junit.jupiter.api.Assertions.*;

public class CommonCategoryValidationSteps {

    @Steps
    AddEditCategoriesPageAPI addEditCategoriesPageAPI;

    // Constants
    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
    private static final String MANDATORY_ERROR = "Category name is mandatory";
    private static final String LENGTH_ERROR = "Category name must be between 3 and 10 characters";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_ERROR = "error";

    // Step definition for verifying 400 Bad Request status for category creation
    @Then("API should return {int} Bad Request status for category creation")
    public void apiShouldReturnBadRequestStatusForCategoryCreation(int expectedStatus) {
        int actualStatus = addEditCategoriesPageAPI.getStatusCode();
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        System.out.println("API Validation Test");
        System.out.println("Endpoint: POST /api/categories");
        System.out.println("Expected Status: " + expectedStatus);
        System.out.println("Actual Status: " + actualStatus);
        System.out.println("Response Body: " + responseBody);

        assertEquals(expectedStatus, actualStatus,
                String.format("Expected status code %d (Bad Request), but got: %d\nResponse: %s",
                        expectedStatus, actualStatus, responseBody));

        System.out.println("✓ Status code " + expectedStatus + " verified successfully");
    }

    // Step definition for verifying validation error message for category creation
    @Then("Response body should contain validation error message for category creation")
    public void responseBodyShouldContainValidationErrorMessageForCategoryCreation() {
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body should not be null");

        // Verify overall error structure
        String message = addEditCategoriesPageAPI.getJsonPathValue(FIELD_MESSAGE);
        assertNotNull(message, "Error message should not be null");
        assertEquals(VALIDATION_FAILED_MESSAGE, message,
                "Expected '" + VALIDATION_FAILED_MESSAGE + "' message");

        // Verify details object exists
        Object details = addEditCategoriesPageAPI.getJsonPathValue(FIELD_DETAILS);
        assertNotNull(details, "Error details should not be null");

        // Extract the validation error for 'name' field
        String nameError = addEditCategoriesPageAPI.getJsonPathValue(FIELD_DETAILS + "." + FIELD_NAME);
        assertNotNull(nameError, "Validation error for 'name' field should exist");

        // Accept either validation message as both are valid for empty/invalid string
        boolean isValidError = nameError.equals(MANDATORY_ERROR) ||
                nameError.equals(LENGTH_ERROR);

        assertTrue(isValidError,
                buildValidationErrorMessage(nameError));

        logValidationErrorVerification(message, nameError);
        System.out.println("✓ Validation error message verified for category creation");
    }

    @And("Response body should contain error type {string}")
    public void responseBodyShouldContainErrorType(String expectedErrorType) {
        String actualErrorType = addEditCategoriesPageAPI.getJsonPathValue(FIELD_ERROR);

        assertNotNull(actualErrorType, "Error type should not be null");
        assertEquals(expectedErrorType, actualErrorType,
                String.format("Expected error type '%s' but got '%s'",
                        expectedErrorType, actualErrorType));

        System.out.println("✓ Error type verified: " + actualErrorType);
    }

    @And("Response body should contain validation error message")
    public void responseBodyShouldContainValidationErrorMessage() {
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body should not be null");

        // Verify overall error structure
        String message = addEditCategoriesPageAPI.getJsonPathValue(FIELD_MESSAGE);
        assertNotNull(message, "Error message should not be null");
        assertEquals(VALIDATION_FAILED_MESSAGE, message,
                "Expected '" + VALIDATION_FAILED_MESSAGE + "' message");

        // Verify details object exists
        Object details = addEditCategoriesPageAPI.getJsonPathValue(FIELD_DETAILS);
        assertNotNull(details, "Error details should not be null");

        // Extract the validation error for 'name' field
        String nameError = addEditCategoriesPageAPI.getJsonPathValue(FIELD_DETAILS + "." + FIELD_NAME);
        assertNotNull(nameError, "Validation error for 'name' field should exist");

        // Accept either validation message as both are valid for empty/invalid string
        boolean isValidError = nameError.equals(MANDATORY_ERROR) ||
                nameError.equals(LENGTH_ERROR);

        assertTrue(isValidError,
                buildValidationErrorMessage(nameError));

        logValidationErrorVerification(message, nameError);
    }

    // helper methods - validation errors

    private void logValidationErrorVerification(String message, String nameError) {
        System.out.println("✓ Validation error structure verified:");
        System.out.println("  - Message: " + message);
        System.out.println("  - Field: " + FIELD_NAME);
        System.out.println("  - Error: " + nameError);
    }

    private String buildValidationErrorMessage(String actualError) {
        return String.format("Expected validation error for invalid category name.\n" +
                "Expected one of:\n" +
                "  - '%s'\n" +
                "  - '%s'\n" +
                "But got: '%s'", MANDATORY_ERROR, LENGTH_ERROR, actualError);
    }
}