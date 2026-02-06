package starter.stepdefinitions.ui.plants;

import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;
import starter.pages.LoginPage;
import starter.pages.Plants.PlantsAddPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlantsAddSteps {
    private static final Logger logger = LoggerFactory.getLogger(PlantsAddSteps.class);

    @Managed
    WebDriver driver;

    LoginPage loginPage = new LoginPage();
    PlantsAddPage plantsAddPage = new PlantsAddPage();

    // ==================== Test Case: Add Plant Button Visibility ====================
    
    // REMOVED DUPLICATE: @Given("Admin is logged in") - Already exists in LoginSteps.java

    @And("Admin is on plant list page")
    public void adminIsOnPlantListPage() {
        plantsAddPage.openPage();
        assertTrue(plantsAddPage.isOnPlantsListPage(), "Admin is not on plant list page");
        logger.info("Admin is on plant list page");
    }

    @Then("Plant list page should be displayed")
    public void plantListPageShouldBeDisplayed() {
        assertTrue(plantsAddPage.isPlantListPageDisplayed(), 
               "Plant list page is not properly displayed");
        logger.info("Plant list page is displayed correctly");
    }

    @When("Admin observes the {string} button")
    public void adminObservesButton(String buttonName) {
        logger.info("Admin is observing the '{}' button", buttonName);
    }

    @Then("{string} button should be visible")
    public void buttonShouldBeVisible(String buttonName) {
        assertTrue(plantsAddPage.isAddPlantButtonVisible(), 
               buttonName + " button is not visible");
        logger.info("{} button is visible", buttonName);
    }

    @And("{string} button should be enabled and clickable")
    public void buttonShouldBeEnabledAndClickable(String buttonName) {
        assertTrue(plantsAddPage.isAddPlantButtonEnabled(), 
               buttonName + " button is not enabled");
        assertTrue(plantsAddPage.isAddPlantButtonClickable(), 
               buttonName + " button is not clickable");
        logger.info("{} button is enabled and clickable", buttonName);
    }

    // ==================== Test Case: Verify Admin can add plant with valid data ====================
    
    @And("At least one sub-category exists")
    public void atLeastOneSubCategoryExists() {
        // This is a precondition, assumed true for UI test. Could add API/database check if needed.
        logger.info("Assuming at least one sub-category exists.");
    }

    @And("Admin is on Add Plant page")
    public void adminIsOnAddPlantPage() {
        plantsAddPage.openAddPlantPage();
        logger.info("Admin is on Add Plant page");
    }

    @When("Admin enters plant name {string}")
    public void adminEntersPlantName(String name) {
        plantsAddPage.enterPlantName(name);
        logger.info("Entered plant name: {}", name);
    }

    @And("Admin selects a sub-category from dropdown")
    public void adminSelectSubCategory() {
        plantsAddPage.selectSubCategory();
        logger.info("Selected sub-category from dropdown");
    }

    @And("Admin enters price {string}")
    public void adminEntersPrice(String price) {
        plantsAddPage.enterPrice(price);
        logger.info("Entered price: {}", price);
    }

    @And("Admin enters quantity {string}")
    public void adminEntersQuantity(String quantity) {
        plantsAddPage.enterQuantity(quantity);
        logger.info("Entered quantity: {}", quantity);
    }

    @And("Admin clicks the Save button")
    public void adminClicksSaveButton() {
        plantsAddPage.clickSaveButton();
        logger.info("Clicked Save button");
    }

    @Then("System displays success message")
    public void systemDisplaysSuccessMessage() {
        assertTrue(plantsAddPage.isSuccessMessageDisplayed(), "Success message not displayed");
        logger.info("Success message displayed");
    }

    @And("User is redirected to plant list page")
    public void userIsRedirectedToPlantListPage() {
        assertTrue(plantsAddPage.isOnPlantListPageAfterAdd(), "Not redirected to plant list page");
        logger.info("Redirected to plant list page");
    }

    @And("New plant {string} appears in the list")
    public void newPlantAppearsInList(String plantName) {
        assertTrue(plantsAddPage.isPlantInList(plantName), "New plant not found in list");
        logger.info("New plant '{}' appears in the list", plantName);
    }

    // ==================== Test Case: Price validation (> 0) ====================
    
    @Then("Error message {string} is displayed below price field in red")
    public void errorMessageDisplayedBelowPriceField(String expectedMessage) {
        assertTrue(plantsAddPage.isPriceValidationErrorDisplayed(expectedMessage),
            "Expected price validation error not displayed or not in red color");
        logger.info("Price validation error displayed: {}", expectedMessage);
    }

    // ==================== Test Case: Plant name length validation (3-25 chars) ====================
    
    @Then("Error message {string} is displayed below name field in red")
    public void errorMessageDisplayedBelowNameField(String expectedMessage) {
        assertTrue(plantsAddPage.isNameValidationErrorDisplayed(expectedMessage),
            "Expected validation error not displayed or not in red color");
        logger.info("Validation error displayed: {}", expectedMessage);
    }

    @And("Plant is not created")
    public void plantIsNotCreated() {
        // Optionally check that we are still on the add page or that no success message is shown
        boolean stillOnAddPage = plantsAddPage.getDriver().getCurrentUrl().contains("/ui/plants/add");
        boolean noSuccess = !plantsAddPage.isSuccessMessageDisplayed();
        assertTrue(stillOnAddPage && noSuccess, "Plant was created or redirected unexpectedly");
        logger.info("Plant is not created as expected");
    }

    // ==================== Test Case: Low Badge Display ====================

    @When("Admin navigates to {string}")
    public void adminNavigatesToUrl(String url) {
        plantsAddPage.getDriver().get("http://localhost:8080" + url);
        logger.info("Admin navigated to: {}", url);
    }

    @When("Admin locates plant with quantity = {int}")
    public void adminLocatesPlantWithQuantity(int quantity) {
        assertTrue(plantsAddPage.isPlantRowVisible(String.valueOf(quantity)), 
                   "Plant with quantity " + quantity + " not found");
        logger.info("Located plant with quantity: {}", quantity);
    }

    @When("Admin observes plant row")
    public void adminObservesPlantRow() {
        logger.info("Admin is observing the plant row");
    }

    @Then("Plant row is visible")
    public void plantRowIsVisible() {
        assertTrue(plantsAddPage.isPlantRowVisible("3"), 
                   "Plant row is not visible");
        logger.info("Plant row is visible");
    }

    @Then("{string} badge is displayed next to the plant")
    public void badgeIsDisplayedNextToPlant(String badgeType) {
        assertTrue(plantsAddPage.isLowBadgeDisplayedForQuantity("3"), 
                   badgeType + " badge is not displayed");
        logger.info("{} badge is displayed next to the plant", badgeType);
    }

    @Then("Badge indicates low stock status")
    public void badgeIndicatesLowStockStatus() {
        assertTrue(plantsAddPage.verifyLowBadgeIndicatesLowStock(), 
                   "Badge does not correctly indicate low stock status");
        logger.info("Badge correctly indicates low stock status");
    }
}