package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.time.Duration;

@DefaultUrl("http://localhost:8080/ui/categories")
public class EditCategoryPage extends PageObject {

    // Category Name input field
    @FindBy(css = "input#name")
    public WebElementFacade categoryNameInput;

    // Parent Category dropdown
    @FindBy(css = "select#parentId")
    public WebElementFacade parentCategoryDropdown;

    // Save button
    @FindBy(css = "button[type='submit'].btn.btn-primary")
    public WebElementFacade saveButton;

    // Success message
    @FindBy(css = ".alert-success, .alert.alert-success")
    public WebElementFacade successMessage;

    // Validation error message for Category Name field
    @FindBy(xpath = "//input[@id='name']/following-sibling::div[@class='invalid-feedback'] | //input[@id='name']/parent::div//div[contains(@class, 'invalid-feedback')] | //input[@id='name']/parent::div//span[contains(text(), 'required')]")
    public WebElementFacade categoryNameValidationMessage;

    // Page heading/title
    @FindBy(css = "h1, h2")
    public WebElementFacade pageHeading;

    /**
     * Verify if the Edit Category page is displayed
     * 
     * @return true if on Edit Category page
     */
    public boolean isOnEditCategoryPage() {
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(driver -> driver.getCurrentUrl().matches(".*/ui/categories/edit/\\d+.*"));

        // Use find() method instead of relying on @FindBy injection
        try {
            WebElementFacade categoryInput = find(By.cssSelector("input#name"));
            categoryInput.waitUntilVisible();
            return true;
        } catch (Exception e) {
            System.out.println("Error waiting for category name input: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get the current value in the Category Name input field
     * 
     * @return current category name value
     */
    public String getCategoryNameValue() {
        try {
            WebElementFacade categoryInput = find(By.cssSelector("input#name"));
            categoryInput.waitUntilVisible();
            String value = categoryInput.getValue();
            System.out.println("Current Category Name value: " + value);
            return value;
        } catch (Exception e) {
            System.out.println("Error getting category name value: " + e.getMessage());
            return "";
        }
    }

    /**
     * Clear and update the Category Name field with new value
     * 
     * @param newCategoryName the new category name to set
     */
    public void updateCategoryName(String newCategoryName) {
        try {
            WebElementFacade categoryInput = find(By.cssSelector("input#name"));
            categoryInput.waitUntilVisible();
            categoryInput.clear();
            categoryInput.type(newCategoryName);
            System.out.println("Updated category name to: " + newCategoryName);
        } catch (Exception e) {
            System.out.println("Error updating category name: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click the Save button to save changes
     */
    public void clickSaveButton() {
        try {
            WebElementFacade saveBtn = find(By.cssSelector("button[type='submit'].btn.btn-primary"));
            saveBtn.waitUntilClickable().click();
            System.out.println("Clicked Save button");
            // Wait for page to start processing after save
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            System.out.println("Error clicking Save button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for save operation to complete
     */
    public void waitForSaveCompletion() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Check if success message is displayed
     * 
     * @return true if success message is visible
     */
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

    /**
     * Get the text of the success message
     * 
     * @return success message text
     */
    public String getSuccessMessageText() {
        try {
            if (successMessage.isVisible()) {
                return successMessage.getText();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Error getting success message text: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if validation message is displayed
     * 
     * @return true if validation message is visible
     */
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

    /**
     * Get the validation message text
     * 
     * @return validation message text
     */
    public String getValidationMessageText() {
        try {
            return categoryNameValidationMessage.getText();
        } catch (Exception e) {
            System.out.println("Error getting validation message text: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get the page heading text
     * 
     * @return page heading text
     */
    public String getPageHeading() {
        try {
            pageHeading.waitUntilVisible();
            return pageHeading.getText();
        } catch (Exception e) {
            System.out.println("Error getting page heading: " + e.getMessage());
            return "";
        }
    }

    /**
     * Verify the Edit Category page is displayed for a specific category
     * 
     * @param categoryName the expected category name
     * @return true if the page displays the correct category
     */
    public boolean isEditPageForCategory(String categoryName) {
        try {
            isOnEditCategoryPage();
            String currentValue = getCategoryNameValue();
            boolean matches = currentValue.equalsIgnoreCase(categoryName);
            System.out.println("Edit page is for category '" + categoryName + "': " + matches);
            return matches;
        } catch (Exception e) {
            System.out.println("Error verifying edit page for category: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the selected value from Parent Category dropdown
     * 
     * @return selected parent category value
     */
    public String getParentCategoryValue() {
        try {
            String selectedValue = parentCategoryDropdown.getSelectedValue();
            System.out.println("Parent Category dropdown value: " + selectedValue);
            return selectedValue != null ? selectedValue : "";
        } catch (Exception e) {
            System.out.println("Error getting parent category value: " + e.getMessage());
            return "";
        }
    }
}