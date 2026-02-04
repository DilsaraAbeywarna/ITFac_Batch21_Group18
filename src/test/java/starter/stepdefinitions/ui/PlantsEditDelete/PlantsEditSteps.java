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
}
