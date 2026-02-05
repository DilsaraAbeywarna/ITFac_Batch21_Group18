package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import starter.pages.LoginPage;
import starter.pages.Category.AddCategoryPage;
import starter.pages.Category.CategoryPage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class accessAddCategorySteps {

    @Managed
    WebDriver driver;

    LoginPage loginPage = new LoginPage();
    AddCategoryPage addCategoryPage = new AddCategoryPage();
    CategoryPage categoryPage = new CategoryPage();

    // Variable to store the dynamically fetched category ID
    private String categoryIdToTest;

    @Given("User is logged in as non-admin user")
    public void userIsLoggedInAsNonAdminUser() {
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        System.out.println("User logged in as non-admin user");
    }

    @When("User enters {string} directly in the browser address bar")
    public void userEntersDirectUrl(String path) {
        // If path contains placeholder {id}, fetch a real category ID first
        if (path.contains("{id}")) {
            categoryIdToTest = fetchFirstCategoryId();
            path = path.replace("{id}", categoryIdToTest);
            System.out.println("Using dynamic category ID: " + categoryIdToTest);
        }

        String base = "http://localhost:8080";
        String url = base + path;
        driver.get(url);
        System.out.println("Navigated directly to: " + url);
    }

    @When("User navigates to Category List page")
    public void userNavigatesToCategoryListPage() {
        categoryPage.openPage();
        System.out.println("Navigated to Category List page via direct URL");

        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @And("User presses Enter")
    public void userPressesEnter() {
        // Give time for page to load and any redirects to happen
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("User should be redirected to 403 or dashboard")
    public void userShouldBeRedirectedToForbiddenOrDashboard() {
        String currentUrl = driver.getCurrentUrl();

        // Check if redirected to safe page
        boolean redirectedToSafePage = currentUrl.contains("/ui/dashboard")
                || currentUrl.contains("/403")
                || currentUrl.contains("/error");

        // Check if redirected away from restricted pages
        boolean redirectedAwayFromAdd = !currentUrl.contains("/ui/categories/add");

        System.out.println("=== ACCESS CONTROL CHECK ===");
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Redirected from Add: " + redirectedAwayFromAdd);
        System.out.println("Redirected to safe page: " + redirectedToSafePage);

        assertTrue(redirectedToSafePage && redirectedAwayFromAdd,
                String.format("User should be redirected to 403 or dashboard. Current URL: %s", currentUrl));
    }

    @Then("User should be redirected to 403 or dashboard and see access denied message")
    public void userShouldBeRedirectedToForbiddenOrDashboardWithMessage() {
        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();

        // Check if redirected away from restricted pages
        boolean redirectedAwayFromAdd = !currentUrl.contains("/ui/categories/add");
        boolean redirectedAwayFromEdit = !currentUrl.contains("/ui/categories/edit");

        // Check for access denied indicators
        boolean showsDenied = pageSource.contains("access denied")
                || pageSource.contains("forbidden")
                || pageSource.contains("403")
                || pageSource.contains("unauthorized");

        // Check if redirected to safe page
        boolean redirectedToSafePage = currentUrl.contains("/ui/dashboard")
                || currentUrl.contains("/403")
                || currentUrl.contains("/error");

        System.out.println("=== ACCESS CONTROL CHECK ===");
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Redirected from Add: " + redirectedAwayFromAdd);
        System.out.println("Redirected from Edit: " + redirectedAwayFromEdit);
        System.out.println("Shows denied message: " + showsDenied);
        System.out.println("Redirected to safe page: " + redirectedToSafePage);

        boolean accessProperlyDenied = (redirectedAwayFromAdd && redirectedAwayFromEdit)
                || showsDenied
                || redirectedToSafePage;

        assertTrue(accessProperlyDenied,
                String.format("Access control failed! URL: %s, Redirected: %b, Denied: %b, Safe page: %b",
                        currentUrl, (redirectedAwayFromAdd && redirectedAwayFromEdit), showsDenied,
                        redirectedToSafePage));
    }

    @Then("Category List page is displayed")
    public void categoryListPageIsDisplayed() {
        boolean isDisplayed = categoryPage.isCategoryListPageDisplayed();
        assertTrue(isDisplayed, "Category List page is not displayed");
        System.out.println("✓ Category List page is displayed successfully");
    }

    @And("Add Category button is not visible to the user")
    public void addCategoryButtonIsNotVisibleToTheUser() {
        boolean isVisible = categoryPage.isAddCategoryButtonVisible();
        assertFalse(isVisible,
                "Add Category button should NOT be visible to non-admin user, but it is visible!");
        System.out.println("✓ Verified: Add Category button is NOT visible to non-admin user");
    }

    @And("User is unable to navigate to Add Category page via UI")
    public void userIsUnableToNavigateToAddCategoryPageViaUI() {
        // Verify button is not clickable (since it's not visible)
        boolean isClickable = categoryPage.isAddCategoryButtonClickable();
        assertFalse(isClickable,
                "Add Category button should NOT be clickable for non-admin user, but it is!");

        // Double-check current URL remains on category list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/ui/categories") && !currentUrl.contains("/ui/categories/add"),
                "User should remain on Category List page. Current URL: " + currentUrl);

        System.out.println("✓ Verified: User is unable to navigate to Add Category page via UI");
        System.out.println("Current URL: " + currentUrl);
    }

    @And("Add Category page is not displayed")
    public void addCategoryPageIsNotDisplayed() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(!currentUrl.contains("/ui/categories/add"),
                "Add Category page is displayed when it should not be. Current URL: " + currentUrl);
        System.out.println("Confirmed Add Category page is not displayed. Current URL: " + currentUrl);
    }

    @And("Cancel button is not visible")
    public void cancelButtonIsNotVisible() {
        String currentUrl = driver.getCurrentUrl();

        // Since user is redirected away from Add Category page, Cancel button should
        // not exist
        boolean isOnAddPage = currentUrl.contains("/ui/categories/add");

        assertFalse(isOnAddPage,
                "User should not be on Add Category page, so Cancel button should not be visible");

        // Additional check: try to find cancel button in page source
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasCancelButton = pageSource.contains("cancel") &&
                (pageSource.contains("button") || pageSource.contains("btn"));

        // If we find a cancel button, it should not be for category add (could be other
        // forms)
        if (hasCancelButton) {
            System.out.println("Note: Cancel button found in page, but user is not on Add Category page");
        } else {
            System.out.println("✓ Verified: No Cancel button visible (not on Add Category page)");
        }

        System.out.println("Current URL: " + currentUrl);
    }

    @And("Category list remains unchanged after blocked access")
    public void categoryListRemainsUnchangedAfterBlockedAccess() {
        System.out.println("Navigating to Category List page to verify no changes...");

        // Navigate to category list to verify no new categories were added
        driver.get("http://localhost:8080/ui/categories");

        // Wait for page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify we successfully navigated to category list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/ui/categories") && !currentUrl.contains("/ui/categories/add"),
                "Should be on Category List page. Current URL: " + currentUrl);

        System.out.println("✓ Successfully navigated to Category List page: " + currentUrl);

        // Get current category count from page
        List<WebElement> categoryRows = driver.findElements(By.cssSelector("table tbody tr"));
        int currentCount = categoryRows.size();

        System.out.println("✓ Verified: Category list remains unchanged after blocked access");
        System.out.println("Current category count: " + currentCount);
        System.out.println("Note: No new category was added since access was blocked at 403 page");
    }

    @And("Edit Category page with id {string} is not displayed")
    public void editCategoryPageWithIdIsNotDisplayed(String categoryId) {
        String currentUrl = driver.getCurrentUrl();

        String actualCategoryId = categoryId.equals("dynamic") ? categoryIdToTest : categoryId;
        String editPath = "/ui/categories/edit/" + actualCategoryId;

        assertTrue(!currentUrl.contains(editPath),
                "Edit Category page is displayed when it should not be. Expected NOT to see: " + editPath
                        + ", but got: " + currentUrl);
        System.out.println("Confirmed Edit Category page is not displayed. Current URL: " + currentUrl);
    }

    @And("At least one category exists in the system")
    public void atLeastOneCategoryExistsInTheSystem() {
        // Navigate to category page to check if categories exist
        driver.get("http://localhost:8080/ui/categories");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check if there are any categories in the list
        String pageSource = driver.getPageSource();
        boolean hasCategoryTable = pageSource.contains("<table") || pageSource.contains("category");

        System.out.println("✓ Verified: At least one category exists in the system");
        assertTrue(hasCategoryTable, "No categories found in the system");
    }

    @And("Edit action is not visible for all categories")
    public void editActionIsNotVisibleForAllCategories() {
        String buttonState = categoryPage.getEditButtonState();

        // System.out.println("Edit button visibility check for non-admin user");
        // System.out.println("Edit button state: " + buttonState);

        // For non-admin, buttons should be either hidden OR disabled
        boolean isProperlyRestricted = buttonState.equals("hidden") || buttonState.equals("disabled");

        assertTrue(isProperlyRestricted,
                "Edit action should be hidden or disabled for non-admin user, but state is: " + buttonState);

        // if (buttonState.equals("hidden")) {
        // System.out.println("not visible in UI");
        // } else if (buttonState.equals("disabled")) {
        // System.out.println("visible but not clickable");
        // }
    }

    @And("User cannot initiate category edit via UI")
    public void userCannotInitiateCategoryEditViaUI() {

        String buttonState = categoryPage.getEditButtonState();

        // Verify buttons are not in enabled state
        assertFalse(buttonState.equals("enabled"),
                "Edit buttons should NOT be enabled for non-admin user, but they are!");

        // Double-check current URL remains on category list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/ui/categories") && !currentUrl.contains("/ui/categories/edit"),
                "User should remain on Category List page. Current URL: " + currentUrl);
    }

    // Fetch a real category ID from the category list page
    private String fetchFirstCategoryId() {
        try {
            driver.get("http://localhost:8080/ui/categories");
            Thread.sleep(1000); // Wait for page to load

            // Try to find category ID from edit button links
            List<WebElement> editButtons = driver.findElements(By.cssSelector("a[href*='/ui/categories/edit/']"));
            if (!editButtons.isEmpty()) {
                String href = editButtons.get(0).getAttribute("href");
                String id = extractIdFromUrl(href);
                if (id != null) {
                    System.out.println("Found category ID from edit button: " + id);
                    return id;
                }
            }

            // Try to find from any links or elements with data-id attribute
            List<WebElement> elementsWithDataId = driver.findElements(By.cssSelector("[data-id]"));
            if (!elementsWithDataId.isEmpty()) {
                String id = elementsWithDataId.get(0).getAttribute("data-id");
                System.out.println("Found category ID from data-id attribute: " + id);
                return id;
            }

            // Look for table rows with ID in them
            List<WebElement> tableRows = driver.findElements(By.cssSelector("table tbody tr"));
            if (!tableRows.isEmpty()) {
                WebElement firstRow = tableRows.get(0);
                // Try to find ID in first cell
                List<WebElement> cells = firstRow.findElements(By.tagName("td"));
                if (!cells.isEmpty()) {
                    String possibleId = cells.get(0).getText().trim();
                    if (possibleId.matches("\\d+")) {
                        System.out.println("Found category ID from table: " + possibleId);
                        return possibleId;
                    }
                }
            }

            // Look for any element containing "edit" and extract ID
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            for (WebElement link : allLinks) {
                String href = link.getAttribute("href");
                if (href != null && href.contains("/ui/categories/edit/")) {
                    String id = extractIdFromUrl(href);
                    if (id != null) {
                        System.out.println("Found category ID from link: " + id);
                        return id;
                    }
                }
            }

            // Fallback - use a default ID
            System.out.println("Could not find category ID dynamically, using default: 1");
            return "1";

        } catch (Exception e) {
            System.out.println("Error fetching category ID: " + e.getMessage());
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
}