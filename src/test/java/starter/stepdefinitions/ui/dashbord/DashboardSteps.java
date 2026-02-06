package starter.stepdefinitions.ui.dashbord;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.DashboardPage;
import starter.pages.LoginPage;
import starter.pages.Category.CategoryPage;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.model.time.InternalSystemClock;

public class DashboardSteps {
    @Managed
    WebDriver driver;

    @Steps
    DashboardPage dashboardPage;

    @Steps
    LoginPage loginPage;

    @Steps
    CategoryPage categoryPage;

    // Render Dashbord Page Successfully
    @Given("Admin logged in and is on the dashbord page")
    public void adminIsOnDashBordPage() {
        loginPage.setDriver(driver);
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.isDashboardDisplayed();
    }

    @Then("Check the header is displayed with title {string}")
    public void enterCredentials(String headerTitle) {
        dashboardPage.isHeaderVisibleWithText(headerTitle);
    }

    @Then("Check the side menu is rendered with releveant menu texts")
    public void verifySideMenu() {
        dashboardPage.isSideMenuVisibleWithTexts(
                java.util.Arrays.asList("Dashboard", "Categories", "Plants", "Sales", "Inventory"));
    }

    @Then("Check the 04 card componenets are rendered")
    public void verifyCards() {
        System.out.println("Test case 01 running");
        dashboardPage.areDashboardCardsVisible(4);
    }

    // Change Backgorund Color Of Menu Buttons When Hover
    @When("Admin hovers over the menu in the side menu")
    public void adminHoversOverMenu() {
        dashboardPage.hoverOnSideMenu();
    }

    @Then("The menu background color should change to {string}")
    public void verifyMenuBackgroundColor(String expectedHexColor) {
        dashboardPage.verifyHoveredMenuBackgroundColor(expectedHexColor);
    }

    // Scale up Sales Categories Inventory Plants Cards When Hovers On Each Card
    @When("Admin hovers over the card item in the dashboard")
    public void adminHoversOverCardItem() {
        dashboardPage.hoverOnDashboardCard();
    }

    @Then("The card shows a motion on y axis")
    public void verifyCardMotion() {
        dashboardPage.verifyDashboardCardMotion();
    }

    // Show Category Summary Information On Category Card
    @Then("Admin navigates to dashboard page")
    public void adminNavigatesToDashboardPage() {
        dashboardPage.setDriver(driver);
        dashboardPage.openPage();
    }

    @Then("Admin should see {int} main categories on dashboard")
    public void adminShouldSeeMainCategoriesOnDashboard(int expectedMainCount) {
        dashboardPage.verifyMainCategoryCount(expectedMainCount);
    }

    @Then("Admin should see {int} sub categories on dashboard")
    public void adminShouldSeeSubCategoriesOnDashboard(int expectedSubCount) {
        dashboardPage.verifySubCategoryCount(expectedSubCount);
    }

}
