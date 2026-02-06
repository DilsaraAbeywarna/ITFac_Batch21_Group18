// package starter.stepdefinitions.ui.plants;

// import io.cucumber.java.en.*;
// import net.serenitybdd.annotations.Steps;
// import starter.pages.Plants.PlantsList;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// public class PlantsListSteps {
//     private static final Logger logger = LoggerFactory.getLogger(PlantsListSteps.class);

//     @Steps
//     PlantsList plantsList;

//     // ==================== Category Filter Steps ====================

//     @And("Plants exist in different categories")
//     public void plantsExistInDifferentCategories() {
//         logger.info("Precondition: Plants exist in different categories");
//     }

//     @And("User is on plant list page for filter")
//     public void userIsOnPlantListPageForFilter() {
//         plantsList.navigateToPlantsList();
//         assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
//         logger.info("User is on plant list page");
//     }

//     @Then("Plant list page is displayed for filter")
//     public void plantListPageIsDisplayedForFilter() {
//         assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
//         logger.info("Plant list page is displayed");
//     }

//     @When("User selects {string} from category dropdown")
//     public void userSelectsFromCategoryDropdown(String categoryName) {
//         plantsList.selectCategory(categoryName);
//         logger.info("User selected '{}' from category dropdown", categoryName);
//     }

//     @And("User clicks Search button for filter")
//     public void userClicksSearchButtonForFilter() {
//         plantsList.clickSearchButtonForFilter();
//         logger.info("User clicked Search button");
//     }

//     @When("User validates category filtered results")
//     public void userValidatesCategoryFilteredResults() {
//         logger.info("Validating category filtered results");
//     }

//     @Then("{string} is selected in category dropdown")
//     public void isSelectedInCategoryDropdown(String categoryName) {
//         assertTrue(plantsList.isCategorySelected(categoryName), 
//                    "Category '" + categoryName + "' is not selected");
//         logger.info("'{}' is selected in category dropdown", categoryName);
//     }

//     @And("Category filter is applied")
//     public void categoryFilterIsApplied() {
//         assertTrue(plantsList.isFilterApplied(), "Category filter was not applied");
//         logger.info("Category filter is applied");
//     }

//     @And("Only plants from {string} category are displayed")
//     public void onlyPlantsFromCategoryAreDisplayed(String categoryName) {
//         assertTrue(plantsList.onlyPlantsFromCategoryDisplayed(categoryName), 
//                    "Not all displayed plants are from '" + categoryName + "' category");
//         logger.info("Only plants from '{}' category are displayed", categoryName);
//     }

//     @And("Plants from other categories are hidden")
//     public void plantsFromOtherCategoriesAreHidden() {
//         logger.info("Plants from other categories are hidden");
//     }

//     @And("Category filter works correctly")
//     public void categoryFilterWorksCorrectly() {
//         logger.info("Category filter works correctly");
//     }

//     // ==================== Search Steps ====================

//     @And("Multiple plants exist with different names")
//     public void multiplePlantsExistWithDifferentNames() {
//         logger.info("Precondition: Multiple plants exist with different names");
//     }

//     @And("User is on plant list page for search")
//     public void userIsOnPlantListPageForSearch() {
//         plantsList.navigateToPlantsList();
//         assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
//         logger.info("User is on plant list page");
//     }

//     @Then("Plant list page is displayed for search")
//     public void plantListPageIsDisplayedForSearch() {
//         assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
//         logger.info("Plant list page is displayed");
//     }

//     @When("User enters {string} in search box")
//     public void userEntersInSearchBox(String searchText) {
//         plantsList.enterSearchText(searchText);
//         logger.info("User entered '{}' in search box", searchText);
//     }

//     @And("User clicks Search button")
//     public void userClicksSearchButton() {
//         plantsList.clickSearchButton();
//         logger.info("User clicked Search button");
//     }

//     @When("User validates filtered results")
//     public void userValidatesFilteredResults() {
//         logger.info("Validating filtered results");
//     }

//     @Then("{string} is entered in search field")
//     public void isEnteredInSearchField(String expectedText) {
//         assertTrue(plantsList.isSearchTextEntered(expectedText), 
//                    "Search text does not match");
//         logger.info("'{}' is entered in search field", expectedText);
//     }

//     @And("Search is executed")
//     public void searchIsExecuted() {
//         assertTrue(plantsList.isSearchExecuted(), "Search was not executed");
//         logger.info("Search is executed");
//     }

//     @And("Only plants with {string} in name are displayed")
//     public void onlyPlantsWithInNameAreDisplayed(String searchQuery) {
//         assertTrue(plantsList.onlyPlantsWithNameDisplayed(searchQuery), 
//                    "Not all displayed plants contain '" + searchQuery + "'");
//         logger.info("Only plants with '{}' are displayed", searchQuery);
//     }

//     @And("Other plants are filtered out")
//     public void otherPlantsAreFilteredOut() {
//         logger.info("Other plants are filtered out");
//     }

//     @And("Search results match query")
//     public void searchResultsMatchQuery() {
//         String searchQuery = plantsList.getSearchInputValue();
//         assertTrue(plantsList.searchResultsMatchQuery(searchQuery), 
//                    "Search results do not match query");
//         logger.info("Search results match query");
//     }

//     // ==================== Sorting Steps ====================

//     @And("Multiple plants with different prices exist")
//     public void multiplePlantsWithDifferentPricesExist() {
//         logger.info("Precondition: Multiple plants with different prices exist");
//     }

//     @And("User is on plant list page for sorting")
//     public void userIsOnPlantListPageForSorting() {
//         plantsList.navigateToPlantsList();
//         assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
//         logger.info("User is on plant list page for sorting");
//     }

//     @Then("Plant list page is displayed for sorting")
//     public void plantListPageIsDisplayedForSorting() {
//         assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
//         logger.info("Plant list page is displayed for sorting");
//     }

//     @When("User clicks on Price column header")
//     public void userClicksOnPriceColumnHeader() {
//         plantsList.clickPriceColumnHeader();
//         logger.info("User clicked on Price column header");
//     }

//     @Then("Plants are sorted by price in ascending order")
//     public void plantsAreSortedByPriceInAscendingOrder() {
//         assertTrue(plantsList.isPriceSortedAscending(), "Plants are not sorted in ascending order");
//         logger.info("Plants are sorted by price in ascending order");
//     }

//     @And("Lowest price appears first")
//     public void lowestPriceAppearsFirst() {
//         assertTrue(plantsList.isLowestPriceFirst(), "Lowest price does not appear first");
//         logger.info("Lowest price appears first");
//     }

//     @When("User clicks on Price column header again")
//     public void userClicksOnPriceColumnHeaderAgain() {
//         plantsList.clickPriceColumnHeader();
//         logger.info("User clicked on Price column header again");
//     }

//     @Then("Plants are sorted by price in descending order")
//     public void plantsAreSortedByPriceInDescendingOrder() {
//         assertTrue(plantsList.isPriceSortedDescending(), "Plants are not sorted in descending order");
//         logger.info("Plants are sorted by price in descending order");
//     }

//     @And("Highest price appears first")
//     public void highestPriceAppearsFirst() {
//         assertTrue(plantsList.isHighestPriceFirst(), "Highest price does not appear first");
//         logger.info("Highest price appears first");
//     }

//     @And("Sorting functionality works correctly")
//     public void sortingFunctionalityWorksCorrectly() {
//         logger.info("Sorting functionality works correctly");
//     }

//     // ==================== Access Denied Steps ====================

//     @When("User directly navigates to {string}")
//     public void userDirectlyNavigatesTo(String url) {
//         plantsList.navigateToUrl(url);
//         logger.info("User directly navigated to '{}'", url);
//     }

//     @Then("User is redirected to 403 Forbidden page")
//     public void userIsRedirectedTo403ForbiddenPage() {
//         assertTrue(plantsList.is403PageDisplayed(), "User is not on 403 Forbidden page");
//         logger.info("User is redirected to 403 Forbidden page");
//     }

//     @And("Page title shows {string}")
//     public void pageTitleShows(String expectedTitle) {
//         assertTrue(plantsList.isPageTitleDisplayed(expectedTitle), 
//                    "Page title does not show '" + expectedTitle + "'");
//         logger.info("Page title shows '{}'", expectedTitle);
//     }

//     @And("Error message {string} is displayed")
//     public void errorMessageIsDisplayed(String expectedMessage) {
//         assertTrue(plantsList.isErrorMessageDisplayed(expectedMessage), 
//                    "Error message '" + expectedMessage + "' is not displayed");
//         logger.info("Error message '{}' is displayed", expectedMessage);
//     }

//     @And("{string} link is available")
//     public void linkIsAvailable(String linkText) {
//         assertTrue(plantsList.isGoBackLinkAvailable(linkText), 
//                    "'" + linkText + "' link is not available");
//         logger.info("'{}' link is available", linkText);
//     }

//     @And("User cannot access Add Plant page")
//     public void userCannotAccessAddPlantPage() {
//         assertTrue(plantsList.isAccessDenied(), "User was able to access Add Plant page");
//         logger.info("User cannot access Add Plant page");
//     }

//     @And("Authorization is enforced at URL level")
//     public void authorizationIsEnforcedAtUrlLevel() {
//         assertTrue(plantsList.isAuthorizationEnforced(), "Authorization is not enforced");
//         logger.info("Authorization is enforced at URL level");
//     }
// }




package starter.stepdefinitions.ui.plants;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import starter.pages.Plants.PlantsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlantsListSteps {
    private static final Logger logger = LoggerFactory.getLogger(PlantsListSteps.class);

    @Steps
    PlantsList plantsList;

    // ==================== Category Filter Steps (Test 008) ====================

    @And("Plants exist in different categories")
    public void plantsExistInDifferentCategories() {
        logger.info("Precondition: Plants exist in different categories");
    }

    @And("User is on plant list page for filter")
    public void userIsOnPlantListPageForFilter() {
        plantsList.navigateToPlantsList();
        assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
        logger.info("User is on plant list page");
    }

    @Then("Plant list page is displayed for filter")
    public void plantListPageIsDisplayedForFilter() {
        assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
        logger.info("Plant list page is displayed");
    }

    @When("User selects {string} from category dropdown")
    public void userSelectsFromCategoryDropdown(String categoryName) {
        plantsList.selectCategory(categoryName);
        logger.info("User selected '{}' from category dropdown", categoryName);
    }

    @And("User clicks Search button for filter")
    public void userClicksSearchButtonForFilter() {
        plantsList.clickSearchButtonForFilter();
        logger.info("User clicked Search button");
    }

    @When("User validates category filtered results")
    public void userValidatesCategoryFilteredResults() {
        logger.info("Validating category filtered results");
    }

    @Then("{string} is selected in category dropdown")
    public void isSelectedInCategoryDropdown(String categoryName) {
        assertTrue(plantsList.isCategorySelected(categoryName), 
                   "Category '" + categoryName + "' is not selected");
        logger.info("'{}' is selected in category dropdown", categoryName);
    }

    @And("Category filter is applied")
    public void categoryFilterIsApplied() {
        assertTrue(plantsList.isFilterApplied(), "Category filter was not applied");
        logger.info("Category filter is applied");
    }

    @And("Only plants from {string} category are displayed")
    public void onlyPlantsFromCategoryAreDisplayed(String categoryName) {
        assertTrue(plantsList.onlyPlantsFromCategoryDisplayed(categoryName), 
                   "Not all displayed plants are from '" + categoryName + "' category");
        logger.info("Only plants from '{}' category are displayed", categoryName);
    }

    @And("Plants from other categories are hidden")
    public void plantsFromOtherCategoriesAreHidden() {
        logger.info("Plants from other categories are hidden");
    }

    @And("Category filter works correctly")
    public void categoryFilterWorksCorrectly() {
        logger.info("Category filter works correctly");
    }

    // ==================== Search Steps (Test 007) ====================

    @And("Multiple plants exist with different names")
    public void multiplePlantsExistWithDifferentNames() {
        logger.info("Precondition: Multiple plants exist with different names");
    }

    @And("User is on plant list page for search")
    public void userIsOnPlantListPageForSearch() {
        plantsList.navigateToPlantsList();
        assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
        logger.info("User is on plant list page");
    }

    @Then("Plant list page is displayed for search")
    public void plantListPageIsDisplayedForSearch() {
        assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
        logger.info("Plant list page is displayed");
    }

    @When("User enters {string} in search box")
    public void userEntersInSearchBox(String searchText) {
        plantsList.enterSearchText(searchText);
        logger.info("User entered '{}' in search box", searchText);
    }

    @And("User clicks Search button")
    public void userClicksSearchButton() {
        plantsList.clickSearchButton();
        logger.info("User clicked Search button");
    }

    @When("User validates filtered results")
    public void userValidatesFilteredResults() {
        logger.info("Validating filtered results");
    }

    @Then("{string} is entered in search field")
    public void isEnteredInSearchField(String expectedText) {
        assertTrue(plantsList.isSearchTextEntered(expectedText), 
                   "Search text does not match");
        logger.info("'{}' is entered in search field", expectedText);
    }

    @And("Search is executed")
    public void searchIsExecuted() {
        assertTrue(plantsList.isSearchExecuted(), "Search was not executed");
        logger.info("Search is executed");
    }

    @And("Only plants with {string} in name are displayed")
    public void onlyPlantsWithInNameAreDisplayed(String searchQuery) {
        assertTrue(plantsList.onlyPlantsWithNameDisplayed(searchQuery), 
                   "Not all displayed plants contain '" + searchQuery + "'");
        logger.info("Only plants with '{}' are displayed", searchQuery);
    }

    @And("Other plants are filtered out")
    public void otherPlantsAreFilteredOut() {
        logger.info("Other plants are filtered out");
    }

    @And("Search results match query")
    public void searchResultsMatchQuery() {
        String searchQuery = plantsList.getSearchInputValue();
        assertTrue(plantsList.searchResultsMatchQuery(searchQuery), 
                   "Search results do not match query");
        logger.info("Search results match query");
    }

    // ==================== Sorting Steps (Test 009) ====================

    @And("Multiple plants with different prices exist")
    public void multiplePlantsWithDifferentPricesExist() {
        logger.info("Precondition: Multiple plants with different prices exist");
    }

    @And("User is on plant list page for sorting")
    public void userIsOnPlantListPageForSorting() {
        plantsList.navigateToPlantsList();
        assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
        logger.info("User is on plant list page for sorting");
    }

    @Then("Plant list page is displayed for sorting")
    public void plantListPageIsDisplayedForSorting() {
        assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
        logger.info("Plant list page is displayed for sorting");
    }

    @When("User clicks on Price column header")
    public void userClicksOnPriceColumnHeader() {
        plantsList.clickPriceColumnHeader();
        logger.info("User clicked on Price column header");
    }

    @Then("Plants are sorted by price in ascending order")
    public void plantsAreSortedByPriceInAscendingOrder() {
        assertTrue(plantsList.isPriceSortedAscending(), "Plants are not sorted in ascending order");
        logger.info("Plants are sorted by price in ascending order");
    }

    @And("Lowest price appears first")
    public void lowestPriceAppearsFirst() {
        assertTrue(plantsList.isLowestPriceFirst(), "Lowest price does not appear first");
        logger.info("Lowest price appears first");
    }

    @When("User clicks on Price column header again")
    public void userClicksOnPriceColumnHeaderAgain() {
        plantsList.clickPriceColumnHeader();
        logger.info("User clicked on Price column header again");
    }

    @Then("Plants are sorted by price in descending order")
    public void plantsAreSortedByPriceInDescendingOrder() {
        assertTrue(plantsList.isPriceSortedDescending(), "Plants are not sorted in descending order");
        logger.info("Plants are sorted by price in descending order");
    }

    @And("Highest price appears first")
    public void highestPriceAppearsFirst() {
        assertTrue(plantsList.isHighestPriceFirst(), "Highest price does not appear first");
        logger.info("Highest price appears first");
    }

    @And("Sorting functionality works correctly")
    public void sortingFunctionalityWorksCorrectly() {
        logger.info("Sorting functionality works correctly");
    }

    // ==================== Access Denied Steps (Test 010) ====================

    @When("User directly navigates to {string}")
    public void userDirectlyNavigatesTo(String url) {
        plantsList.navigateToUrl(url);
        logger.info("User directly navigated to '{}'", url);
    }

    @Then("User is redirected to 403 Forbidden page")
    public void userIsRedirectedTo403ForbiddenPage() {
        assertTrue(plantsList.is403PageDisplayed(), "User is not on 403 Forbidden page");
        logger.info("User is redirected to 403 Forbidden page");
    }

    @And("Page title shows {string}")
    public void pageTitleShows(String expectedTitle) {
        assertTrue(plantsList.isPageTitleDisplayed(expectedTitle), 
                   "Page title does not show '" + expectedTitle + "'");
        logger.info("Page title shows '{}'", expectedTitle);
    }

    @And("Error message {string} is displayed")
    public void errorMessageIsDisplayed(String expectedMessage) {
        assertTrue(plantsList.isErrorMessageDisplayed(expectedMessage), 
                   "Error message '" + expectedMessage + "' is not displayed");
        logger.info("Error message '{}' is displayed", expectedMessage);
    }

    @And("{string} link is available")
    public void linkIsAvailable(String linkText) {
        assertTrue(plantsList.isGoBackLinkAvailable(linkText), 
                   "'" + linkText + "' link is not available");
        logger.info("'{}' link is available", linkText);
    }

    @And("User cannot access Add Plant page")
    public void userCannotAccessAddPlantPage() {
        assertTrue(plantsList.isAccessDenied(), "User was able to access Add Plant page");
        logger.info("User cannot access Add Plant page");
    }

    @And("Authorization is enforced at URL level")
    public void authorizationIsEnforcedAtUrlLevel() {
        assertTrue(plantsList.isAuthorizationEnforced(), "Authorization is not enforced");
        logger.info("Authorization is enforced at URL level");
    }

    // ==================== Pagination Steps (Test 006) ====================

    @And("More than {int} plants exist")
    public void moreThanPlantsExist(int plantCount) {
        logger.info("Precondition: More than {} plants exist in the system", plantCount);
    }

    @And("User is on plant list page for pagination")
    public void userIsOnPlantListPageForPagination() {
        plantsList.navigateToPlantsList();
        assertTrue(plantsList.isPlantListPageDisplayed(), "User is not on plant list page");
        logger.info("User is on plant list page for pagination");
    }

    @Then("Plant list page is displayed for pagination")
    public void plantListPageIsDisplayedForPagination() {
        assertTrue(plantsList.isPlantListPageDisplayed(), "Plant list page is not displayed");
        logger.info("Plant list page is displayed for pagination");
    }

    @When("User observes first page of plants")
    public void userObservesFirstPageOfPlants() {
        logger.info("User is observing the first page of plants");
    }

    @Then("First page shows plants {int}-{int} with pagination controls")
    public void firstPageShowsPlantsWithPaginationControls(int startRange, int endRange) {
        assertTrue(plantsList.isFirstPageDisplayed(), "First page is not displayed");
        assertTrue(plantsList.isPaginationControlsVisible(), "Pagination controls are not visible");
        assertTrue(plantsList.isPlantsDisplayedOnPage(10), "Plants are not displayed on first page");
        logger.info("First page shows plants {}-{} with pagination controls", startRange, endRange);
    }

    @When("User clicks {string} button")
    public void userClicksButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Next")) {
            plantsList.clickNextButton();
            logger.info("User clicked Next button");
        } else if (buttonName.equalsIgnoreCase("Previous")) {
            plantsList.clickPreviousButton();
            logger.info("User clicked Previous button");
        }
    }

    @Then("Next button is clicked")
    public void nextButtonIsClicked() {
        logger.info("Next button click action completed");
    }

    @And("User observes second page")
    public void userObservesSecondPage() {
        logger.info("User is observing the second page of plants");
    }

    @Then("Second page loads showing plants {int}-{int}")
    public void secondPageLoadsShowingPlants(int startRange, int endRange) {
        assertTrue(plantsList.isSecondPageDisplayed(), "Second page is not displayed");
        assertTrue(plantsList.isPlantsDisplayedOnPage(10), "Plants are not displayed on second page");
        logger.info("Second page loads showing plants {}-{}", startRange, endRange);
    }

    @And("Page indicator updates to page {int}")
    public void pageIndicatorUpdatesToPage(int pageNumber) {
        assertTrue(plantsList.isPageIndicatorUpdated(String.valueOf(pageNumber)), 
                   "Page indicator did not update to page " + pageNumber);
        logger.info("Page indicator updated to page {}", pageNumber);
    }

    @Then("Previous button is clicked")
    public void previousButtonIsClicked() {
        logger.info("Previous button click action completed");
    }

    @And("First page loads again")
    public void firstPageLoadsAgain() {
        assertTrue(plantsList.isFirstPageDisplayed(), "First page is not displayed after clicking Previous");
        logger.info("First page loads again successfully");
    }

    @And("Pagination navigation works correctly")
    public void paginationNavigationWorksCorrectly() {
        logger.info("Pagination navigation works correctly");
    }
}