package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;

@DefaultUrl("/ui/categories/add")
public class AddCategoryPage extends PageObject {

    // Category Name input field
    @FindBy(css = "input#name")
    public WebElementFacade categoryNameInput;

    // Parent Category dropdown
    @FindBy(css = "select#parentId")
    public WebElementFacade parentCategoryDropdown;

    // Save button
    @FindBy(css = "button[type='submit'].btn.btn-primary")
    public WebElementFacade saveButton;

    // Cancel button - NEW
    @FindBy(css = "a[href='/ui/categories'].btn.btn-secondary")
    public WebElementFacade cancelButton;

    // Success message
    @FindBy(css = ".alert-success, .alert.alert-success")
    public WebElementFacade successMessage;

    // Validation error message for Category Name field
    @FindBy(xpath = "//input[@id='name']/following-sibling::div[@class='invalid-feedback'] | //input[@id='name']/parent::div//div[contains(@class, 'invalid-feedback')] | //input[@id='name']/parent::div//span[contains(text(), 'required')]")
    public WebElementFacade categoryNameValidationMessage;

    public boolean isOnAddCategoryPage() {
        System.out.println("Waiting for Add Category page...");
        System.out.println("Current URL before wait: " + getDriver().getCurrentUrl());

        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(driver -> {
                        String currentUrl = driver.getCurrentUrl();
                        System.out.println("Checking URL: " + currentUrl);
                        return currentUrl.contains("/ui/categories/add") || currentUrl.contains("/ui/categories/edit");
                    });

            System.out.println("URL verified. Waiting for Category Name input field...");
            categoryNameInput.waitUntilVisible();
            System.out.println("Category Name input field is visible");

            return true;
        } catch (Exception e) {
            System.out.println("Failed to verify Add Category page. Final URL: " + getDriver().getCurrentUrl());
            throw e;
        }
    }

    public void enterCategoryName(String categoryName) {
        categoryNameInput.waitUntilVisible().type(categoryName);
        System.out.println("Entered category name: " + categoryName);
    }

    public void leaveParentCategoryEmpty() {
        // Verify that the parent category dropdown is set to empty/Main Category
        String selectedValue = parentCategoryDropdown.getSelectedValue();
        System.out.println("Parent Category dropdown value: " + selectedValue);

        if (selectedValue == null || selectedValue.isEmpty()) {
            System.out.println("Parent Category is already empty (Main Category)");
        }
    }

    public void clickSaveButton() {
        saveButton.waitUntilClickable().click();
        System.out.println("Clicked Save button");
        // Wait for page to start processing after save
        waitABit(1000);
    }

    // NEW METHOD - Click Cancel button
    public void clickCancelButton() {
        cancelButton.waitUntilClickable().click();
        System.out.println("Clicked Cancel button");
        // Wait for navigation to complete
        waitABit(1000);
    }

    // NEW METHOD - Check if Cancel button is visible
    public boolean isCancelButtonVisible() {
        try {
            cancelButton.waitUntilVisible();
            boolean isVisible = cancelButton.isVisible();
            System.out.println("Cancel button visibility: " + isVisible);
            return isVisible;
        } catch (Exception e) {
            System.out.println("Cancel button not found: " + e.getMessage());
            return false;
        }
    }

    public void waitForSaveCompletion() {
        waitABit(1500);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            successMessage.waitUntilVisible();
            boolean isVisible = successMessage.isVisible();
            System.out.println("Success message visibility: " + isVisible);
            if (isVisible) {
                System.out.println("Success message text: " + successMessage.getText());
            }
            return isVisible;
        } catch (Exception e) {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }
    }

    public String getCategoryNameValue() {
        return categoryNameInput.getValue();
    }

    public boolean isValidationMessageDisplayed() {
        try {
            categoryNameValidationMessage.waitUntilVisible();
            boolean isVisible = categoryNameValidationMessage.isVisible();
            System.out.println("Validation message visibility: " + isVisible);
            if (isVisible) {
                System.out.println("Validation message text: " + categoryNameValidationMessage.getText());
            }
            return isVisible;
        } catch (Exception e) {
            System.out.println("Validation message not found: " + e.getMessage());
            return false;
        }
    }

    public String getValidationMessageText() {
        try {
            return categoryNameValidationMessage.getText();
        } catch (Exception e) {
            System.out.println("Error getting validation message text: " + e.getMessage());
            return "";
        }
    }

    public boolean isValidationMessageContainsRequiredText() {
        try {
            String messageText = getValidationMessageText();
            boolean containsRequired = messageText.toLowerCase().contains("required");
            System.out.println("Validation message contains 'required': " + containsRequired);
            return containsRequired;
        } catch (Exception e) {
            System.out.println("Error checking validation message: " + e.getMessage());
            return false;
        }
    }
}