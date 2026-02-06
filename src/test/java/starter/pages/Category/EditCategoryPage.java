package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

@DefaultUrl("http://localhost:8080/ui/categories/edit")
public class EditCategoryPage extends PageObject {

    // ========== LOCATORS ==========
    private By categoryNameInputLocator = By.cssSelector("input#name");
    private By parentCategoryDropdownLocator = By.cssSelector("select#parentId");
    private By saveButtonLocator = By.cssSelector("button[type='submit'].btn-primary");
    private By pageHeadingLocator = By.cssSelector("h1, h2");
    private By successMessageLocator = By.cssSelector(".alert-success");
    private By validationMessageLocator = By.cssSelector("input#name ~ .invalid-feedback");

    // ========== PAGE STATE VERIFICATION ==========

    public boolean isOnEditCategoryPage() {
        try {
            logInfo("Waiting for Edit Category URL...");
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(driver -> isEditCategoryUrl());

            logInfo("✓ Edit Category URL verified: " + getCurrentUrl());

            // Wait for page to load
            waitABit(2000);

            // Wait for the input field to be present and visible
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(driver -> {
                        try {
                            WebElement element = driver.findElement(categoryNameInputLocator);
                            return element != null && element.isDisplayed();
                        } catch (Exception e) {
                            return false;
                        }
                    });

            logInfo("Edit Category page loaded successfully");
            return true;

        } catch (Exception e) {
            logError("Failed to verify Edit Category page. URL: " + getCurrentUrl());
            logError("Error: " + e.getMessage());
            throw new AssertionError("Not on Edit Category page", e);
        }
    }

    private boolean isEditCategoryUrl() {
        String currentUrl = getCurrentUrl();
        return currentUrl.matches(".*/ui/categories/edit/\\d+.*");
    }

    public boolean isEditPageForCategory(String categoryName) {
        try {
            isOnEditCategoryPage();
            String currentValue = getCategoryNameValue();
            boolean matches = currentValue.equalsIgnoreCase(categoryName);
            logInfo("Edit page for '" + categoryName + "': " + matches +
                    " (Current: '" + currentValue + "')");
            return matches;
        } catch (Exception e) {
            logError("Error verifying category match: " + e.getMessage());
            return false;
        }
    }

    // ========== INPUT METHODS ==========

    public String getCategoryNameValue() {
        WebElement input = getDriver().findElement(categoryNameInputLocator);
        waitFor(input).waitUntilVisible();
        String value = input.getAttribute("value");
        logInfo("Current category name: " + value);
        return value;
    }

    public void updateCategoryName(String newCategoryName) {
        WebElement input = getDriver().findElement(categoryNameInputLocator);
        waitFor(input).waitUntilVisible();
        input.clear();
        input.sendKeys(newCategoryName);
        logInfo("Updated category name to: " + newCategoryName);
    }

    public void clearCategoryName() {
        WebElement input = getDriver().findElement(categoryNameInputLocator);
        waitFor(input).waitUntilVisible();
        input.clear();
        logInfo("Cleared category name");
    }

    public String getParentCategoryValue() {
        try {
            WebElement dropdown = getDriver().findElement(parentCategoryDropdownLocator);
            Select select = new Select(dropdown);
            String selectedValue = select.getFirstSelectedOption().getAttribute("value");
            logInfo("Parent category value: " + selectedValue);
            return selectedValue != null ? selectedValue : "";
        } catch (Exception e) {
            logError("Error getting parent category: " + e.getMessage());
            return "";
        }
    }

    // ========== BUTTON ACTIONS ==========

    public void clickSaveButton() {
        WebElement button = getDriver().findElement(saveButtonLocator);
        waitFor(button).waitUntilClickable();
        button.click();
        waitForPageLoad();
        logInfo("Clicked Save button");
    }

    public boolean isSaveButtonEnabled() {
        try {
            WebElement button = getDriver().findElement(saveButtonLocator);
            return button.isEnabled();
        } catch (Exception e) {
            logError("Save button not enabled");
            return false;
        }
    }

    // ========== MESSAGE VERIFICATION ==========

    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement message = getDriver().findElement(successMessageLocator);
            waitFor(message).waitUntilVisible();
            String messageText = message.getText();
            logInfo("Success message: " + messageText);
            return true;
        } catch (Exception e) {
            logInfo("Success message not displayed");
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            WebElement message = getDriver().findElement(successMessageLocator);
            if (message.isDisplayed()) {
                return message.getText();
            }
        } catch (Exception e) {
            logError("Error getting success message: " + e.getMessage());
        }
        return "";
    }

    // ========== VALIDATION METHODS ==========

    public boolean isValidationMessageDisplayed() {
        try {
            WebElement message = getDriver().findElement(validationMessageLocator);
            if (message.isDisplayed()) {
                logInfo("Validation message: " + message.getText());
                return true;
            }
            return false;
        } catch (Exception e) {
            logInfo("No validation message displayed");
            return false;
        }
    }

    public String getValidationMessageText() {
        try {
            WebElement message = getDriver().findElement(validationMessageLocator);
            if (message.isDisplayed()) {
                return message.getText();
            }
        } catch (Exception e) {
            logError("Error getting validation message: " + e.getMessage());
        }
        return "";
    }

    // ========== PAGE INFO METHODS ==========

    public String getPageHeading() {
        try {
            WebElement heading = getDriver().findElement(pageHeadingLocator);
            waitFor(heading).waitUntilVisible();
            return heading.getText();
        } catch (Exception e) {
            logError("Error getting page heading: " + e.getMessage());
            return "";
        }
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