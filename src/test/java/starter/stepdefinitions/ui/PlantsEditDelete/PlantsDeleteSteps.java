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

public class PlantsDeleteSteps {

    @Managed
    WebDriver driver;

    @Steps
    PlantsEditDelete plantsEditDelete;

    private LoginPage loginPage = new LoginPage();

    // UI_PLANTDELETE_DELETEBUTTON_13

    @Given("Test User is logged in and has navigated to the Plant page for delete")
    public void testUserIsLoggedInAndHasNavigatedToThePlantPageForDelete() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
    }

    @When("Test User navigates to the Plant List page for delete")
    public void testUserNavigatesToThePlantListPageForDelete() {
        plantsEditDelete.setDriver(driver);
        plantsEditDelete.navigateToPlantsPage();
    }

    @Then("Delete button is not displayed for the Test User role")
    public void deleteButtonIsNotDisplayedForTheTestUserRole() {
        assertTrue(
            !plantsEditDelete.isDeleteButtonVisible(),
            "Delete button should not be visible for Test User role"
        );
    }
}
