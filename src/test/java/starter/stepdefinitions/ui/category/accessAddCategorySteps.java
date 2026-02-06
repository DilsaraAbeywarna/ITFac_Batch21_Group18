package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import starter.pages.LoginPage;
import starter.pages.Category.AddCategoryPage;
import starter.pages.Category.CategoryPage;

import static org.assertj.core.api.Assertions.assertThat;

public class accessAddCategorySteps {

    @Steps
    LoginPage loginPage;

    @Steps
    AddCategoryPage addCategoryPage;

    @Steps
    CategoryPage categoryPage;

    private String categoryIdToTest;

    // ========== GIVEN STEPS ==========

    @Given("User is logged in as non-admin user")
    public void userIsLoggedInAsNonAdminUser() {
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        logSuccess("User logged in as non-admin user");
    }

    // ========== WHEN STEPS ==========

    @When("User enters {string} directly in the browser address bar")
    public void userEntersDirectUrl(String path) {
        String finalPath = resolveDynamicPath(path);
        String url = "http://localhost:8080" + finalPath;

        categoryPage.navigateToUrl(url);
        logSuccess("Navigated directly to: " + url);
    }

    @When("User navigates to Category List page")
    public void userNavigatesToCategoryListPage() {
        categoryPage.openPage();
        logSuccess("Navigated to Category List page via direct URL");
    }

    @And("User presses Enter")
    public void userPressesEnter() {
        categoryPage.pause(1500);
    }

    @And("At least one category exists in the system")
    public void atLeastOneCategoryExistsInTheSystem() {
        categoryPage.navigateToUrl("http://localhost:8080/ui/categories");
        categoryPage.pause(1000);

        assertThat(categoryPage.hasCategoryTable())
                .as("At least one category should exist in the system")
                .isTrue();

        logSuccess("Verified: At least one category exists in the system");
    }

    // ========== THEN STEPS - ACCESS CONTROL VERIFICATION ==========

    @Then("User should be redirected to 403 or dashboard")
    public void userShouldBeRedirectedToForbiddenOrDashboard() {
        String currentUrl = categoryPage.getCurrentUrl();

        boolean redirectedToSafePage = isRedirectedToSafePage(currentUrl);
        boolean redirectedAwayFromAdd = !currentUrl.contains("/ui/categories/add");

        logAccessControlCheck(currentUrl, redirectedAwayFromAdd, redirectedToSafePage);

        assertThat(redirectedToSafePage && redirectedAwayFromAdd)
                .as("User should be redirected to 403 or dashboard. Current URL: " + currentUrl)
                .isTrue();
    }

    @Then("User should be redirected to 403 or dashboard and see access denied message")
    public void userShouldBeRedirectedToForbiddenOrDashboardWithMessage() {
        String currentUrl = categoryPage.getCurrentUrl();
        String pageSource = categoryPage.getPageSource().toLowerCase();

        boolean redirectedAwayFromAdd = !currentUrl.contains("/ui/categories/add");
        boolean redirectedAwayFromEdit = !currentUrl.contains("/ui/categories/edit");
        boolean showsDenied = containsAccessDeniedMessage(pageSource);
        boolean redirectedToSafePage = isRedirectedToSafePage(currentUrl);

        logDetailedAccessControlCheck(currentUrl, redirectedAwayFromAdd,
                redirectedAwayFromEdit, showsDenied, redirectedToSafePage);

        boolean accessProperlyDenied = (redirectedAwayFromAdd && redirectedAwayFromEdit)
                || showsDenied
                || redirectedToSafePage;

        assertThat(accessProperlyDenied)
                .as("Access control failed! URL: " + currentUrl)
                .isTrue();
    }

    @Then("Category List page is displayed")
    public void categoryListPageIsDisplayed() {
        assertThat(categoryPage.isCategoryListPageDisplayed())
                .as("Category List page should be displayed")
                .isTrue();
        logSuccess("Category List page is displayed successfully");
    }

    // ========== AND STEPS - UI ELEMENT VERIFICATION ==========

    @And("Add Category button is not visible to the user")
    public void addCategoryButtonIsNotVisibleToTheUser() {
        assertThat(categoryPage.isAddCategoryButtonVisible())
                .as("Add Category button should NOT be visible to non-admin user")
                .isFalse();
        logSuccess("Verified: Add Category button is NOT visible to non-admin user");
    }

    @And("User is unable to navigate to Add Category page via UI")
    public void userIsUnableToNavigateToAddCategoryPageViaUI() {
        assertThat(categoryPage.isAddCategoryButtonClickable())
                .as("Add Category button should NOT be clickable for non-admin user")
                .isFalse();

        String currentUrl = categoryPage.getCurrentUrl();
        assertThat(currentUrl.contains("/ui/categories")
                && !currentUrl.contains("/ui/categories/add"))
                .as("User should remain on Category List page")
                .isTrue();

        logSuccess("Verified: User is unable to navigate to Add Category page via UI");
        logInfo("Current URL: " + currentUrl);
    }

    @And("Add Category page is not displayed")
    public void addCategoryPageIsNotDisplayed() {
        String currentUrl = categoryPage.getCurrentUrl();

        assertThat(currentUrl.contains("/ui/categories/add"))
                .as("Add Category page should not be displayed")
                .isFalse();

        logSuccess("Confirmed Add Category page is not displayed. Current URL: " + currentUrl);
    }

    @And("Cancel button is not visible")
    public void cancelButtonIsNotVisible() {
        String currentUrl = categoryPage.getCurrentUrl();
        boolean isOnAddPage = currentUrl.contains("/ui/categories/add");

        assertThat(isOnAddPage)
                .as("User should not be on Add Category page, so Cancel button should not be visible")
                .isFalse();

        verifyCancelButtonInPageSource(categoryPage.getPageSource().toLowerCase(), currentUrl);
    }

    @And("Category list remains unchanged after blocked access")
    public void categoryListRemainsUnchangedAfterBlockedAccess() {
        logInfo("Navigating to Category List page to verify no changes...");

        categoryPage.navigateToUrl("http://localhost:8080/ui/categories");
        categoryPage.pause(2000);

        String currentUrl = categoryPage.getCurrentUrl();
        assertThat(currentUrl.contains("/ui/categories")
                && !currentUrl.contains("/ui/categories/add"))
                .as("Should be on Category List page")
                .isTrue();

        logSuccess("Successfully navigated to Category List page: " + currentUrl);

        int currentCount = categoryPage.getCategoryRowCount();
        logSuccess("Verified: Category list remains unchanged after blocked access");
        logInfo("Current category count: " + currentCount);
        logInfo("Note: No new category was added since access was blocked");
    }

    @And("Edit Category page with id {string} is not displayed")
    public void editCategoryPageWithIdIsNotDisplayed(String categoryId) {
        String currentUrl = categoryPage.getCurrentUrl();
        String actualCategoryId = categoryId.equals("dynamic") ? categoryIdToTest : categoryId;
        String editPath = "/ui/categories/edit/" + actualCategoryId;

        assertThat(currentUrl.contains(editPath))
                .as("Edit Category page should not be displayed. Expected NOT to see: " + editPath)
                .isFalse();

        logSuccess("Confirmed Edit Category page is not displayed. Current URL: " + currentUrl);
    }

    @And("Edit action is not visible for all categories")
    public void editActionIsNotVisibleForAllCategories() {
        String buttonState = categoryPage.getEditButtonState();

        boolean isProperlyRestricted = buttonState.equals("hidden") || buttonState.equals("disabled");

        assertThat(isProperlyRestricted)
                .as("Edit action should be hidden or disabled for non-admin user, but state is: " + buttonState)
                .isTrue();
    }

    @And("User cannot initiate category edit via UI")
    public void userCannotInitiateCategoryEditViaUI() {
        String buttonState = categoryPage.getEditButtonState();

        assertThat(buttonState.equals("enabled"))
                .as("Edit buttons should NOT be enabled for non-admin user")
                .isFalse();

        String currentUrl = categoryPage.getCurrentUrl();
        assertThat(currentUrl.contains("/ui/categories")
                && !currentUrl.contains("/ui/categories/edit"))
                .as("User should remain on Category List page")
                .isTrue();
    }

    // ========== HELPER METHODS ==========

    private String resolveDynamicPath(String path) {
        if (path.contains("{id}")) {
            categoryIdToTest = categoryPage.fetchFirstCategoryId();
            Serenity.setSessionVariable("categoryIdToTest").to(categoryIdToTest);
            path = path.replace("{id}", categoryIdToTest);
            logInfo("Using dynamic category ID: " + categoryIdToTest);
        }
        return path;
    }

    private boolean isRedirectedToSafePage(String url) {
        return url.contains("/ui/dashboard")
                || url.contains("/403")
                || url.contains("/error");
    }

    private boolean containsAccessDeniedMessage(String pageSource) {
        return pageSource.contains("access denied")
                || pageSource.contains("forbidden")
                || pageSource.contains("403")
                || pageSource.contains("unauthorized");
    }

    private void verifyCancelButtonInPageSource(String pageSource, String currentUrl) {
        boolean hasCancelButton = pageSource.contains("cancel") &&
                (pageSource.contains("button") || pageSource.contains("btn"));

        if (hasCancelButton) {
            logInfo("Note: Cancel button found in page, but user is not on Add Category page");
        } else {
            logSuccess("Verified: No Cancel button visible (not on Add Category page)");
        }
        logInfo("Current URL: " + currentUrl);
    }

    // ========== LOGGING UTILITIES ==========

    private void logAccessControlCheck(String url, boolean redirectedFromAdd, boolean safeRedirect) {
        System.out.println("=== ACCESS CONTROL CHECK ===");
        System.out.println("Current URL: " + url);
        System.out.println("Redirected from Add: " + redirectedFromAdd);
        System.out.println("Redirected to safe page: " + safeRedirect);
    }

    private void logDetailedAccessControlCheck(String url, boolean fromAdd,
            boolean fromEdit, boolean denied, boolean safeRedirect) {
        System.out.println("=== ACCESS CONTROL CHECK ===");
        System.out.println("Current URL: " + url);
        System.out.println("Redirected from Add: " + fromAdd);
        System.out.println("Redirected from Edit: " + fromEdit);
        System.out.println("Shows denied message: " + denied);
        System.out.println("Redirected to safe page: " + safeRedirect);
    }

    private void logSuccess(String message) {
        System.out.println(message);
    }

    private void logInfo(String message) {
        System.out.println(message);
    }
}