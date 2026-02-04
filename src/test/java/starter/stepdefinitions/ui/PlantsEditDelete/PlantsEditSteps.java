package starter.stepdefinitions.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Managed;
import starter.pages.PlantsEditDelete;
import starter.pages.LoginPage;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PlantsEditSteps {

    @Managed
    WebDriver driver;

    @Steps
    PlantsEditDelete plantsEditDelete;

     private LoginPage loginPage = new LoginPage();

    // UI_PLANTEDIT_NAVIGATION_01

    @Given("Admin user is logged in and on the dashboard")
    public void adminUserIsLoggedInAndOnTheDashboard() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
    }

    @When("Admin user navigates to the Plant List page")
    public void adminUserNavigatesToThePlantListPage() {
        plantsEditDelete.setDriver(driver);
        plantsEditDelete.navigateToPlantsPage();
    }

    @When("Admin user clicks the Edit button of a plant")
    public void adminUserClicksTheEditButtonOfAPlant() {
        plantsEditDelete.clickEditButton();
    }

    @Then("Admin user should be navigated to the Edit Plant page")
    public void adminUserShouldBeNavigatedToTheEditPlantPage() {
        assertTrue(
            plantsEditDelete.isOnEditPlantPage(),
            "Admin user was not navigated to the Edit Plant page"
        );
    }

    // UI_PLANTEDIT_NAMEVALIDATION_02

    @When("Admin user clears the plant name field")
    public void adminUserClearsThePlantNameField() {
        plantsEditDelete.clearPlantNameField();
    }

    @When("Admin user selects a valid sub-category")
    public void adminUserSelectsAValidSubCategory() {
        plantsEditDelete.selectSubCategory("1");
    }

    @When("Admin user enters {string} in the price field")
    public void adminUserEntersPriceInThePriceField(String price) {
        plantsEditDelete.enterPrice(price);
    }

    @When("Admin user enters {string} in the quantity field")
    public void adminUserEntersQuantityInTheQuantityField(String quantity) {
        plantsEditDelete.enterQuantity(quantity);
    }

    @When("Admin user clicks the Save button")
    public void adminUserClicksTheSaveButton() {
        plantsEditDelete.clickSaveButton();
    }

    @Then("Admin user should see the plant name validation error message")
    public void adminUserShouldSeeThePlantNameValidationErrorMessage() {
        assertAll(
            () -> assertTrue(
                plantsEditDelete.isPlantNameErrorMessageDisplayed(),
                "Plant name error message is not displayed"
            ),
            () -> assertTrue(
                plantsEditDelete.getPlantNameErrorMessage().contains("Plant name must be between 3 and 25 characters"),
                "Error message does not contain expected text about character range"
            ),
            () -> assertTrue(
                plantsEditDelete.getPlantNameErrorMessage().contains("Plant name is required"),
                "Error message does not contain expected text about required field"
            )
        );
    }

    // UI_PLANTEDIT_PRICEVALIDATION_03

    @When("Admin user enters a valid plant name")
    public void adminUserEntersAValidPlantName() {
        plantsEditDelete.enterPlantName("Tomato Plant");
    }

    @When("Admin user clears the price field")
    public void adminUserClearsThePriceField() {
        plantsEditDelete.clearPriceField();
    }

    @Then("Admin user should see the price validation error message")
    public void adminUserShouldSeeThePriceValidationErrorMessage() {
        assertAll(
            () -> assertTrue(
                plantsEditDelete.isPriceErrorMessageDisplayed(),
                "Price error message is not displayed"
            ),
            () -> assertTrue(
                plantsEditDelete.getPriceErrorMessage().contains("Price is required"),
                "Error message does not contain expected text about price requirement"
            )
        );
    }

    //UI_PLANTEDIT_QUANTITYVALIDATION_04

     @When("Admin user clears the quantity field")
    public void adminUserClearsThequantityField() {
        plantsEditDelete.clearquantityField();
    }

     @Then("Admin user should see the quantity validation error message")
    public void adminUserShouldSeeThequantityValidationErrorMessage() {
        assertAll(
            () -> assertTrue(
                plantsEditDelete.isquantityErrorMessageDisplayed(),
                "quantity error message is not displayed"
            ),
            () -> assertTrue(
                plantsEditDelete.getquantityErrorMessage().contains("Quantity is required"),
                "Error message does not contain expected text about quantity requirement"
            )
        );
    }

    // UI_PLANTEDIT_CATEGORYVALIDATION_05

    @When("Admin user selects the {string} category option")
    public void adminUserSelectsTheCategoryOption(String categoryValue) {
        plantsEditDelete.selectCategoryByValue(categoryValue);
    }

    @Then("Admin user should see the category validation error message")
    public void adminUserShouldSeeTheCategoryValidationErrorMessage() {
        assertAll(
            () -> assertTrue(
                plantsEditDelete.isCategoryErrorMessageDisplayed(),
                "Category error message is not displayed"
            ),
            () -> assertTrue(
                plantsEditDelete.getCategoryErrorMessage().contains("Category is required"),
                "Error message does not contain expected text about category requirement"
            )
        );
    }

    // UI_PLANTEDIT_SAVEBUTTON_06

    @When("Admin user enters a Quantity {string}")
    public void adminUserEntersAQuantity(String quantity) {
        plantsEditDelete.clearAndEnterQuantity(quantity);
    }

    @When("Admin user enters a Plant name {string}")
    public void adminUserEntersAPlantName(String plantName) {
        plantsEditDelete.clearAndEnterPlantName(plantName);
    }

    @When("Admin user enters a price {string}")
    public void adminUserEntersAPrice(String price) {
        plantsEditDelete.clearAndEnterPrice(price);
    }

    @When("Admin user selects a valid category {string}")
    public void adminUserSelectsAValidCategory(String category) {
        plantsEditDelete.selectCategoryByVisibleText(category);
    }

    @Then("Plant details are saved successfully and user is redirected to Plant List page")
    public void plantDetailsAreSavedSuccessfullyAndUserIsRedirectedToPlantListPage() {
        assertTrue(
            plantsEditDelete.isOnPlantListPage(),
            "User was not redirected to the Plant List page after saving"
        );
    }

    // UI_PLANTEDIT_CANCELBUTTON_07

    @When("Admin user modifies the plant name field")
    public void adminUserModifiesThePlantNameField() {
        plantsEditDelete.clearAndEnterPlantName("ModifiedPlantName");
    }

    @When("Admin user clicks the Cancel button")
    public void adminUserClicksTheCancelButton() {
        plantsEditDelete.clickCancelButton();
    }

    @Then("Admin user is redirected to plants page")
    public void adminUserIsRedirectedToPlantsPage() {
        assertTrue(
            plantsEditDelete.isOnPlantListPage(),
            "Admin user was not redirected to the Plant List page after clicking Cancel"
        );
    }

    // UI_PLANTEDIT_VIEWEDITEDLIST_10

    @Given("Test User is logged in and has navigated to the Plant page")
    public void testUserIsLoggedInAndHasNavigatedToThePlantPage() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
    }

    @When("Test User navigates to the Plant List page")
    public void testUserNavigatesToThePlantListPage() {
        plantsEditDelete.setDriver(driver);
        plantsEditDelete.navigateToPlantsPage();
    }

    @Then("edited Plant list displayed")
    public void editedPlantListDisplayed() {
        assertTrue(
            plantsEditDelete.isOnPlantListPage(),
            "Test User is not on the Plant List page"
        );
    }

}
