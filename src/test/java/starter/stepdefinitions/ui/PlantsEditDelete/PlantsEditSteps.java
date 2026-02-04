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
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
    }

    @When("Admin user navigates to the Plant List page")
    public void adminUserNavigatesToThePlantListPage() {
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
}
