package starter.stepdefinitions.ui.plants;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import starter.pages.LoginPage;
import starter.pages.Plants.PlantsAddPage;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlantsAddSteps {
    
    @Managed
    WebDriver driver;
    
    LoginPage loginPage = new LoginPage();
    PlantsAddPage plantsAddPage = new PlantsAddPage();

    @Given("Admin is logged in")
    public void adminIsLoggedIn() {
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        System.out.println("Admin logged in successfully");
    }

    @And("Admin is on plant list page")
    public void adminIsOnPlantListPage() {
        plantsAddPage.openPage();
        assertTrue(plantsAddPage.isOnPlantsListPage(), "Admin is not on plant list page");
        System.out.println("Admin is on plant list page");
    }

    @Then("Plant list page should be displayed")
    public void plantListPageShouldBeDisplayed() {
        assertTrue(plantsAddPage.isPlantListPageDisplayed(), 
                   "Plant list page is not properly displayed");
        System.out.println("Plant list page is displayed correctly");
    }

    @When("Admin observes the {string} button")
    public void adminObservesButton(String buttonName) {
        System.out.println("Admin is observing the '" + buttonName + "' button");
    }

    @Then("{string} button should be visible")
    public void buttonShouldBeVisible(String buttonName) {
        assertTrue(plantsAddPage.isAddPlantButtonVisible(), 
                   buttonName + " button is not visible");
        System.out.println(buttonName + " button is visible");
    }

    @And("{string} button should be enabled and clickable")
    public void buttonShouldBeEnabledAndClickable(String buttonName) {
        assertTrue(plantsAddPage.isAddPlantButtonEnabled(), 
                   buttonName + " button is not enabled");
        assertTrue(plantsAddPage.isAddPlantButtonClickable(), 
                   buttonName + " button is not clickable");
        System.out.println(buttonName + " button is enabled and clickable");
    }
}