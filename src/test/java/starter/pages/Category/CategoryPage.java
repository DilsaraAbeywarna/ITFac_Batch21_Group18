package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DefaultUrl("http://localhost:8080/ui/categories")
public class CategoryPage extends PageObject {

    // ========== LOCATORS ==========

    // Navigation
    @FindBy(css = "a[href='/ui/categories']")
    private WebElementFacade categoriesNavLink;

    // Buttons
    @FindBy(css = "a[href='/ui/categories/add'].btn.btn-primary")
    private WebElementFacade addCategoryButton;

    // Page Elements
    @FindBy(css = "h1, h2")
    private WebElementFacade pageHeading;

    @FindBy(css = "table")
    private WebElementFacade categoryTable;

    @FindBy(css = "table tbody tr")
    private List<WebElementFacade> categoryTableRows;

    // Messages
    @FindBy(css = ".alert-success")
    private WebElementFacade successMessage;

    // Edit Actions
    @FindBy(css = "a[href*='/ui/categories/edit/']")
    private List<WebElementFacade> editActionButtons;

    // ========== NAVIGATION METHODS ==========

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/categories");
        waitForPageToLoad();
        logInfo("Navigated to Category List page: " + getCurrentUrl());
    }

    public void navigateToUrl(String url) {
        openUrl(url);
        waitForPageToLoad();
    }

    public void navigateToCategoriesPageViaSidebar() {
        categoriesNavLink.waitUntilClickable().click();
        waitForPageToLoad();
        logInfo("Navigated via sidebar to: " + getCurrentUrl());
    }

    // ========== PUBLIC WAIT METHOD ==========

    public void waitForPageToLoad() {
        waitABit(1000);
    }

    public void pause(long milliseconds) {
        waitABit(milliseconds);
    }

    // ========== PAGE STATE VERIFICATION ==========

    public boolean isCategoryListPageDisplayed() {
        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(driver -> isCategoryListUrl());

            pageHeading.waitUntilVisible();
            categoryTable.waitUntilPresent();

            logInfo("Category list page verified successfully");
            return true;
        } catch (Exception e) {
            logError("Failed to verify category list page: " + e.getMessage());
            return false;
        }
    }

    public boolean isOnCategoryListPage() {
        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(driver -> isCategoryListUrl());

            logInfo("Successfully on category list page");
            return true;
        } catch (Exception e) {
            logError("Not on category list page. Current URL: " + getCurrentUrl());
            throw new AssertionError("Failed to navigate to category list page", e);
        }
    }

    private boolean isCategoryListUrl() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/ui/categories")
                && !currentUrl.contains("/ui/categories/add")
                && !currentUrl.contains("/ui/categories/edit");
    }

    // ========== BUTTON STATE METHODS ==========

    public boolean isAddCategoryButtonVisible() {
        try {
            return addCategoryButton.withTimeoutOf(Duration.ofSeconds(5)).isVisible();
        } catch (Exception e) {
            logInfo("Add Category button not visible");
            return false;
        }
    }

    public boolean isAddCategoryButtonClickable() {
        try {
            addCategoryButton.waitUntilClickable();
            return addCategoryButton.isEnabled() && addCategoryButton.isClickable();
        } catch (Exception e) {
            logInfo("Add Category button not clickable");
            return false;
        }
    }

    public void clickAddCategoryButton() {
        addCategoryButton.waitUntilClickable().click();
        waitForPageToLoad();
        logInfo("Clicked Add Category button");
    }

    // ========== EDIT ACTIONS ==========

    public String getEditButtonState() {
        if (editActionButtons.isEmpty()) {
            logInfo("No edit buttons found - HIDDEN");
            return "hidden";
        }

        for (WebElementFacade button : editActionButtons) {
            if (button.isCurrentlyVisible()) {
                if (button.isEnabled()) {
                    logInfo("Edit button state: ENABLED");
                    return "enabled";
                } else {
                    logInfo("Edit button state: DISABLED");
                    return "disabled";
                }
            }
        }

        logInfo("Edit buttons exist but not visible - HIDDEN");
        return "hidden";
    }

    public boolean areEditButtonsVisible() {
        try {
            waitABit(500);
            boolean hasVisibleButtons = editActionButtons.stream()
                    .anyMatch(WebElementFacade::isVisible);

            logInfo("Edit buttons visibility check - Total: " + editActionButtons.size() +
                    ", Visible: " + hasVisibleButtons);
            return hasVisibleButtons;
        } catch (Exception e) {
            logError("Error checking edit buttons visibility: " + e.getMessage());
            return false;
        }
    }

    public boolean areEditButtonsClickable() {
        try {
            return editActionButtons.stream()
                    .anyMatch(button -> button.isVisible() && button.isClickable());
        } catch (Exception e) {
            logError("Error checking edit buttons clickability: " + e.getMessage());
            return false;
        }
    }

    public void clickEditIconForCategory(String categoryName) {
        String xpath = String.format(
                "//tr[.//td[contains(normalize-space(.), '%s')]]//a[contains(@href, '/ui/categories/edit')]",
                categoryName);

        WebElementFacade editLink = findBy(xpath);
        editLink.waitUntilClickable().click();

        // Add longer wait for page transition
        waitABit(2000); // Increased from 1000
        logInfo("Clicked Edit for category: " + categoryName);
    }

    // ========== CATEGORY DATA METHODS ==========

    public boolean isCategoryInList(String categoryName) {
        try {
            waitABit(500);
            String pageSource = getDriver().getPageSource();
            boolean exists = pageSource.contains(categoryName);
            logInfo("Category '" + categoryName + "' exists: " + exists);
            return exists;
        } catch (Exception e) {
            logError("Error checking category existence: " + e.getMessage());
            return false;
        }
    }

    public int getCategoryRowCount() {
        try {
            categoryTable.waitUntilPresent();
            int count = categoryTableRows.size();
            logInfo("Category count: " + count);
            return count;
        } catch (Exception e) {
            logError("Error getting category count: " + e.getMessage());
            return 0;
        }
    }

    public boolean hasCategoryTable() {
        try {
            return categoryTable.withTimeoutOf(Duration.ofSeconds(5)).isPresent();
        } catch (Exception e) {
            logError("Category table not found");
            return false;
        }
    }

    // ========== MESSAGE VERIFICATION ==========

    public boolean isSuccessMessageDisplayed() {
        try {
            successMessage.waitUntilVisible();
            String messageText = successMessage.getText();
            logInfo("Success message displayed: " + messageText);
            return true;
        } catch (Exception e) {
            logInfo("Success message not displayed");
            return false;
        }
    }

    // ========== PAGE INFO METHODS ==========

    public String getPageHeading() {
        pageHeading.waitUntilVisible();
        return pageHeading.getText();
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getPageSource() {
        return getDriver().getPageSource();
    }

    // ========== VERIFICATION METHODS ==========

    public void verifyOnCategoryPage() {
        pageHeading.shouldBeVisible();
        String currentUrl = getCurrentUrl();
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Expected to be on category page but URL is: " + currentUrl);
        }
    }

    public void verifyPageTitleContainsText(String text) {
        pageHeading.shouldBeVisible();
        pageHeading.shouldContainText(text);
    }

    public void verifyAddCategoryButtonVisible() {
        addCategoryButton.shouldBeVisible();
    }

    // ========== DYNAMIC ID FETCHING ==========

    public String fetchFirstCategoryId() {
        try {
            navigateToUrl("http://localhost:8080/ui/categories");
            waitABit(1000);

            // Extract from edit button href
            List<WebElement> editButtons = getDriver().findElements(
                    By.cssSelector("a[href*='/ui/categories/edit/']"));

            if (!editButtons.isEmpty()) {
                String href = editButtons.get(0).getAttribute("href");
                String id = extractIdFromUrl(href);
                if (id != null) {
                    logInfo("Found category ID: " + id);
                    return id;
                }
            }

            logInfo("No categories found, using default ID: 1");
            return "1";

        } catch (Exception e) {
            logError("Error fetching category ID: " + e.getMessage());
            return "1";
        }
    }

    private String extractIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("/ui/categories/edit/(\\d+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // ========== UTILITY METHODS ==========

    private void logInfo(String message) {
        System.out.println(message);
    }

    private void logError(String message) {
        System.err.println( message);
    }
}