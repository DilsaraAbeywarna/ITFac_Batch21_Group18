package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;

@DefaultUrl("/ui/categories")
public class AddCategoryPage extends PageObject {

    // Sidebar navigation link to Categories page
    @FindBy(css = "a[href='/ui/categories']")
    public WebElementFacade categoriesNavLink;

    // Add A Category button
    @FindBy(css = "a[href='/ui/categories/add'].btn.btn-primary")
    public WebElementFacade addCategoryButton;

    // Alternative locator for Add A Category button
    @FindBy(xpath = "//a[@href='/ui/categories/add' and contains(@class, 'btn-primary')]")
    public WebElementFacade addCategoryButtonAlt;

    // Page heading/title
    @FindBy(css = "h1, h2")
    public WebElementFacade pageHeading;

    // Category list table/container
    @FindBy(css = "table, .category-list, .table")
    public WebElementFacade categoryListContainer;

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

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/categories");
        System.out.println("Explicitly navigated to: " + getDriver().getCurrentUrl());
    }

    public void navigateToCategoriesPageViaSidebar() {
        categoriesNavLink.waitUntilClickable().click();
        waitABit(1000);
        System.out.println("Clicked Categories sidebar link. Current URL: " + getDriver().getCurrentUrl());
    }

    public boolean isCategoryListPageDisplayed() {
        try {
            // Check URL
            boolean urlCorrect = getDriver().getCurrentUrl().contains("/ui/categories")
                    && !getDriver().getCurrentUrl().contains("/ui/categories/add");

            // Check page heading is visible
            boolean headingVisible = pageHeading.isVisible();

            System.out.println("Category list page displayed - URL correct: " + urlCorrect
                    + ", Heading visible: " + headingVisible);

            return urlCorrect && headingVisible;
        } catch (Exception e) {
            System.out.println("Error checking category list page: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddCategoryButtonVisible() {
        try {
            addCategoryButton.waitUntilVisible();
            boolean isVisible = addCategoryButton.isVisible();
            System.out.println("Add A Category button visibility: " + isVisible);
            return isVisible;
        } catch (Exception e) {
            System.out.println("Add A Category button not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddCategoryButtonClickable() {
        try {
            addCategoryButton.waitUntilClickable();
            boolean isClickable = addCategoryButton.isClickable();
            System.out.println("Add A Category button clickable: " + isClickable);
            return isClickable;
        } catch (Exception e) {
            System.out.println("Add A Category button not clickable: " + e.getMessage());
            return false;
        }
    }

    public void clickAddCategoryButton() {
        addCategoryButton.waitUntilClickable().click();
        // Wait for page to start redirecting after click
        waitABit(1000);
        System.out.println("Clicked Add Category button. Current URL: " + getDriver().getCurrentUrl());
    }

    public boolean isOnCategoryListPage() {
        System.out.println("Waiting for Category List page...");
        System.out.println("Current URL before wait: " + getDriver().getCurrentUrl());

        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(driver -> {
                        String currentUrl = driver.getCurrentUrl();
                        System.out.println("Checking URL: " + currentUrl);
                        return currentUrl.contains("/ui/categories") && !currentUrl.contains("/ui/categories/add");
                    });
            return true;
        } catch (Exception e) {
            System.out.println("Failed to verify category list page. Final URL: " + getDriver().getCurrentUrl());
            throw e;
        }
    }

    public boolean isOnAddCategoryPage() {
        System.out.println("Waiting for Add Category page...");
        System.out.println("Current URL before wait: " + getDriver().getCurrentUrl());

        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(driver -> {
                        String currentUrl = driver.getCurrentUrl();
                        System.out.println("Checking URL: " + currentUrl);
                        return currentUrl.contains("/ui/categories/add");
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

    public boolean isCategoryInList(String categoryName) {
        try {
            waitABit(1000);
            String pageSource = getDriver().getPageSource();
            boolean categoryExists = pageSource.contains(categoryName);
            System.out.println("Category '" + categoryName + "' in list: " + categoryExists);
            return categoryExists;
        } catch (Exception e) {
            System.out.println("Error checking if category is in list: " + e.getMessage());
            return false;
        }
    }

    public String getPageHeading() {
        return pageHeading.getText();
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