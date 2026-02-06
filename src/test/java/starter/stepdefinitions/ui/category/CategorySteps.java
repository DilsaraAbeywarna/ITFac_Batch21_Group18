package starter.stepdefinitions.ui.category;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.model.time.InternalSystemClock;
import starter.pages.DashboardPage;
import starter.pages.LoginPage;
import starter.pages.Category.CategoryPage;

public class CategorySteps {
    @Managed
    WebDriver driver;

    @Steps
    LoginPage loginPage;

    @Steps
    DashboardPage dashboardPage;

    @Steps
    CategoryPage categoryPage;

    // Render Dashbord Page Successfully
    @Given("Admin is logged in as admin")
    public void adminIsLoggedIn() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.isDashboardDisplayed();
    }

    @When("Admin navigates to the categories page")
    public void adminNavigatesToCategoriesPage() {
        dashboardPage.clickTheMangeCategoriesButtonInCategoryCard();
        // Wait for the category page to fully load
        categoryPage.verifyOnCategoryPage();
    }

    @Then("Admin should see add a category button")
    public void adminShouldSeeAddCategoryButton() {
        categoryPage.isAddACategoryButtonVisible();
    }

    // Render Category Page Successfully
    @Given("User is logged in as user")
    public void userIsLoggedIn() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
        loginPage.isDashboardDisplayed();
    }

    @When("User navigates to the categories page")
    public void userNavigatesToCategoriesPage() {
        dashboardPage.clickTheMangeCategoriesButtonInCategoryCard();
        // Wait for the category page to fully load
        categoryPage.verifyOnCategoryPage();
    }

    @Then("Sub header exits with Categories text")
    public void subHeaderExitsWithCategoriesText() {
        categoryPage.isSubHeaderVisibleWithText("Categories");
    }

    @Then("Text input component exists")
    public void textInputComponentExists() {
        categoryPage.isInputFeildVisibleWithLabel();
    }

    @Then("Select component is exiting with All Parents selected")
    public void selectComponentIsExitingWithAllParentsSelected() {
        categoryPage.isParentSelectVisible();
    }

    @Then("Button exits with text Search")
    public void buttonExitsWithTextSearch() {
        categoryPage.isSearchButtonVisible();
    }

    @Then("Button exits with text Reset")
    public void buttonExitsWithTextReset() {
        categoryPage.isResetButtonVisible();
    }

    @Then("Table exits")
    public void tableExits() {
        categoryPage.isCategoryTableVisible();
    }

    // Add 15 categories to the System
    @Then("Admin adds {int} categories to the system")
    public void adminAddsCategoriesToSystem(int numberOfCategories) {
        // Define categories to add - PARENTS FIRST, then CHILDREN
        String[][] categories = {
                { "Rose", "" },
                { "Lily", "" },
                { "Tulip", "" },
                { "Daisy", "" },
                { "Orchid", "" },
                { "Sunflower", "" },
                { "Iris", "" },
                { "Peony", "" },
                { "Dahlia", "" },
                { "Jasmine", "" },
                { "Lavender", "" },
                { "Carnation", "" },
                { "Poppy", "" },
                { "Violet", "" },
                { "Marigold", "" }
        };

        System.out.println("Starting to add " + numberOfCategories + " categories...");

        for (int i = 0; i < numberOfCategories && i < categories.length; i++) {
            String categoryName = categories[i][0];
            String parentCategory = categories[i][1];

            System.out.println("\n=== Adding category " + (i + 1) + "/" + numberOfCategories + " ===");

            // Always navigate to categories list page first to refresh the parent dropdown
            // options
            categoryPage.openPage();
            new InternalSystemClock().pauseFor(500); // Small wait for page load

            // Verify we're on categories page
            categoryPage.verifyOnCategoryPage();

            // Click add button
            categoryPage.clickAddCategoryButton();

            // Add the category
            try {
                categoryPage.addNewCategory(categoryName, parentCategory);
            } catch (Exception e) {
                System.err.println("❌ Failed to add category: " + categoryName);
                System.err.println("Error: " + e.getMessage());

                // If it's a parent category issue, try refreshing and retrying
                if (e.getMessage().contains("Cannot locate option") && !parentCategory.isEmpty()) {
                    System.out.println("Retrying after refresh...");
                    categoryPage.openPage();
                    new InternalSystemClock().pauseFor(1000);
                    categoryPage.verifyOnCategoryPage();
                    categoryPage.clickAddCategoryButton();
                    categoryPage.verifyOnAddCategoryPage();
                    categoryPage.addNewCategory(categoryName, parentCategory);
                } else {
                    throw e;
                }
            }

            // Wait for redirect
            new InternalSystemClock().pauseFor(1500);

            // Check current URL after save
            String urlAfterSave = driver.getCurrentUrl();
            System.out.println("URL after save: " + urlAfterSave);

            // If still on add page, navigate back to list page
            if (urlAfterSave.contains("/ui/categories/add")) {
                System.out.println("Still on add page, navigating back to categories list...");
                categoryPage.openPage();
            }

            System.out.println("✓ Successfully added: " + categoryName +
                    (parentCategory.isEmpty() ? " (Main)" : " (Parent: " + parentCategory + ")"));
        }

        System.out.println("\n✓✓✓ All " + numberOfCategories + " categories added successfully! ✓✓✓");
    }

    // Filter the categories by the category name
    @Then("User type the {string} category in the search bar")
    public void userTypeTheCategoryInSearchBar(String categoryName) {
        // DEBUG: Check what's in the table BEFORE searching
        System.out.println("\n🔍 DEBUG: Checking categories in table before search...");

        // Get all visible categories
        List<WebElementFacade> tableRows = categoryPage.findAll("table.table tbody tr");
        System.out.println("📊 Total rows in table: " + tableRows.size());

        if (tableRows.isEmpty()) {
            System.err.println("❌ TABLE IS EMPTY - No categories found!");
        } else {
            System.out.println("📋 Categories currently in table:");
            for (int i = 0; i < Math.min(tableRows.size(), 20); i++) {
                List<WebElementFacade> cells = tableRows.get(i).thenFindAll("td");
                if (cells.size() >= 2) {
                    String categoryInTable = cells.get(1).getText().trim();
                    System.out.println("  " + (i + 1) + ". " + categoryInTable);
                }
            }
        }
        categoryPage.typeInSearchInput(categoryName);
    }

    @Then("User clicks the search button")
    public void userClicksTheSearchButton() {
        categoryPage.clickSearchButton();
    }

    @Then("User should see Rose feild only")
    public void userShouldSeeElectronicFieldOnly() {
        // Wait for search results to load
        new InternalSystemClock().pauseFor(1000);

        // Verify only Electronics-related categories appear
        categoryPage.verifyOnlyCategoryAppears("Rose");
    }

    // Alternative: More flexible version
    @Then("User should see {string} field only")
    public void userShouldSeeFieldOnly(String categoryName) {
        // Wait for search results to load
        new InternalSystemClock().pauseFor(1000);

        // Verify only the specified category appears
        categoryPage.verifyOnlyCategoryAppears(categoryName);
    }

    @Then("User should see the search results")
    public void userShouldSeeSearchResults() {
        int rowCount = categoryPage.getTableRowCount();
        if (rowCount == 0) {
            throw new AssertionError("❌ No search results found");
        }
        System.out.println("✓ Found " + rowCount + " search result(s)");
    }

    @Then("User verifies {string} appears in results")
    public void userVerifiesCategoryInResults(String categoryName) {
        categoryPage.verifyCategoryInSearchResults(categoryName);
    }

    @Then("User clears the search")
    public void userClearsTheSearch() {
        categoryPage.clearSearchInput();
    }

    @Then("User clicks reset button")
    public void userClicksResetButton() {
        categoryPage.clickResetButton();
    }

    // Display list of categories with pagination
    @Then("User should see {int} records in the page")
    public void userShouldSeeRecordsInThePage(int expectedCount) {
        categoryPage.verifyRecordCount(expectedCount);
    }

    @Then("User should see next and previous buttons")
    public void userShouldSeeNextAndPreviousButtons() {
        categoryPage.verifyNextAndPreviousButtonsVisible();
    }

    @Then("User should see {int} page buttons")
    public void userShouldSeePageButtons(int expectedCount) {
        categoryPage.verifyPageButtonCount(expectedCount);
    }

    @When("User clicks the next button")
    public void userClicksTheNextButton() {
        categoryPage.clickNextButton();
    }

    @When("User clicks the previous button")
    public void userClicksThePreviousButton() {
        categoryPage.clickPreviousButton();
    }

    // Display "No Category Found" message when search for non exisiting category
    @Then("User should see {string} message")
    public void userShouldSeeMessage(String expectedMessage) {
        categoryPage.verifyNoCategoryFoundMessage(expectedMessage);
    }

    // Display categories sorted by category Id
    @When("User clicks on the {string} column header")
    public void userClicksOnTheColumnHeader(String columnName) {
        categoryPage.clickColumnHeader(columnName);
    }

    @Then("User should see categories sorted by ID in ascending order")
    public void userShouldSeeCategoriesSortedByIdInAscendingOrder() {
        categoryPage.verifySortedByIdAscending();
        categoryPage.verifySortIcon("ID", "desc");
    }

    @Then("User should see categories sorted by ID in descending order")
    public void userShouldSeeCategoriesSortedByIdInDescendingOrder() {
        categoryPage.verifySortedByIdDescending();
        categoryPage.verifySortIcon("ID", "asc");
    }

    // Show Category Plants Sales Summary Information On Each Card
    @Then("Admin should see {int} total plants on dashboard")
    public void adminShouldSeeTotalPlantsOnDashboard(int expectedTotal) {
        dashboardPage.verifyTotalPlantsCount(expectedTotal);
    }

    @Then("Admin should see {int} low stock plants on dashboard")
    public void adminShouldSeeLowStockPlantsOnDashboard(int expectedLowStock) {
        dashboardPage.verifyLowStockPlantsCount(expectedLowStock);
    }


    @Then("Admin should see revenue {string} on dashboard")
    public void adminShouldSeeRevenueOnDashboard(String expectedRevenue) {
        dashboardPage.verifyRevenueAmount(expectedRevenue);
    }

    @Then("Admin should see {int} sales count on dashboard")
    public void adminShouldSeeSalesCountOnDashboard(int expectedSalesCount) {
        dashboardPage.verifySalesCount(expectedSalesCount);
    }

}
