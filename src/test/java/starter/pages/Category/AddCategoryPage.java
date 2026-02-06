package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;

@DefaultUrl("http://localhost:8080/ui/categories/add")
public class AddCategoryPage extends PageObject {

    // ========== LOCATORS ==========

    // Input Fields
    @FindBy(css = "input#name")
    private WebElementFacade categoryNameInput;

    @FindBy(css = "select#parentId")
    private WebElementFacade parentCategoryDropdown;

    // Action Buttons
    @FindBy(css = "button[type='submit'].btn-primary")
    private WebElementFacade saveButton;

    @FindBy(css = "a[href='/ui/categories'].btn-secondary")
    private WebElementFacade cancelButton;

    // Messages
    @FindBy(css = ".alert-success")
    private WebElementFacade successMessage;

    @FindBy(css = "input#name ~ .invalid-feedback")
    private WebElementFacade categoryNameValidationMessage;

    // Alternative validation message locator
    @FindBy(xpath = "//input[@id='name']/following-sibling::div[contains(@class, 'invalid-feedback')]")
    private WebElementFacade validationMessageAlt;

    // ========== PAGE STATE VERIFICATION ==========

    public boolean isOnAddCategoryPage() {
        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(driver -> isAddCategoryUrl());

            categoryNameInput.waitUntilPresent();
            logInfo("Add Category page loaded successfully");
            return true;
        } catch (Exception e) {
            logError("Failed to verify Add Category page. URL: " + getCurrentUrl());
            throw new AssertionError("Not on Add Category page", e);
        }
    }

    private boolean isAddCategoryUrl() {
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/ui/categories/add") ||
                currentUrl.contains("/ui/categories/edit");
    }

    // ========== INPUT METHODS ==========

    public void enterCategoryName(String categoryName) {
        categoryNameInput.waitUntilVisible();
        categoryNameInput.clear();
        categoryNameInput.type(categoryName);
        logInfo("Entered category name: " + categoryName);
    }

    public String getCategoryNameValue() {
        categoryNameInput.waitUntilPresent();
        return categoryNameInput.getValue();
    }

    public void clearCategoryName() {
        categoryNameInput.waitUntilVisible();
        categoryNameInput.clear();
        logInfo("Cleared category name field");
    }

    public void leaveParentCategoryEmpty() {
        String selectedValue = parentCategoryDropdown.getSelectedValue();
        logInfo("Parent Category value: " + (selectedValue == null || selectedValue.isEmpty()
                ? "Empty (Main Category)"
                : selectedValue));
    }

    // ========== BUTTON ACTIONS ==========

    public void clickSaveButton() {
        saveButton.waitUntilClickable().click();
        waitForPageLoad();
        logInfo("Clicked Save button");
    }

    public void clickCancelButton() {
        cancelButton.waitUntilClickable().click();
        waitForPageLoad();
        logInfo("Clicked Cancel button");
    }

    public boolean isCancelButtonVisible() {
        try {
            return cancelButton.withTimeoutOf(Duration.ofSeconds(5)).isVisible();
        } catch (Exception e) {
            logInfo("Cancel button not visible");
            return false;
        }
    }

    public boolean isSaveButtonEnabled() {
        try {
            saveButton.waitUntilPresent();
            return saveButton.isEnabled();
        } catch (Exception e) {
            logError("Save button not found or not enabled");
            return false;
        }
    }

    // ========== MESSAGE VERIFICATION ==========

    public boolean isSuccessMessageDisplayed() {
        try {
            successMessage.waitUntilVisible();
            String messageText = successMessage.getText();
            logInfo("Success message: " + messageText);
            return true;
        } catch (Exception e) {
            logInfo("Success message not displayed");
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            if (successMessage.isVisible()) {
                return successMessage.getText();
            }
        } catch (Exception e) {
            logError("Error getting success message: " + e.getMessage());
        }
        return "";
    }

    // ========== VALIDATION METHODS ==========

    public boolean isValidationMessageDisplayed() {
        try {
            // Try primary locator first
            if (categoryNameValidationMessage.withTimeoutOf(Duration.ofSeconds(2)).isVisible()) {
                logInfo("Validation message: " + categoryNameValidationMessage.getText());
                return true;
            }

            // Try alternative locator
            if (validationMessageAlt.withTimeoutOf(Duration.ofSeconds(2)).isVisible()) {
                logInfo("Validation message (alt): " + validationMessageAlt.getText());
                return true;
            }

            logInfo("No validation message displayed");
            return false;
        } catch (Exception e) {
            logInfo("Validation message not found");
            return false;
        }
    }

    public String getValidationMessageText() {
        try {
            if (categoryNameValidationMessage.isVisible()) {
                return categoryNameValidationMessage.getText();
            }
            if (validationMessageAlt.isVisible()) {
                return validationMessageAlt.getText();
            }
        } catch (Exception e) {
            logError("Error getting validation message: " + e.getMessage());
        }
        return "";
    }

    public boolean isValidationMessageContainsRequiredText() {
        String messageText = getValidationMessageText().toLowerCase();
        boolean containsRequired = messageText.contains("required");
        logInfo("Validation contains 'required': " + containsRequired);
        return containsRequired;
    }

    // ========== UTILITY METHODS ==========

    public void waitForSaveCompletion() {
        waitABit(1500);
    }

    private void waitForPageLoad() {
        waitABit(1000);
    }

    private String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    private void logInfo(String message) {
        System.out.println(message);
    }

    private void logError(String message) {
        System.err.println(message);
    }
}