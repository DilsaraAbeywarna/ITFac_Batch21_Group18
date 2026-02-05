package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.pages.SellPlantPage;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateSaleSteps {

    @Steps
    SellPlantPage sellPlantPage;


    /* ================= Form Steps ================= */

    @When("Admin selects {string} plant")
    public void adminSelectsPlant(String plantName) {
        sellPlantPage.selectPlant(plantName);
    }

    @When("Admin enters quantity {string}")
    public void adminEntersQuantity(String quantity) {
        sellPlantPage.enterQuantity(Integer.parseInt(quantity));
    }

    @When("Admin clicks Sell button")
    public void adminClicksSell() {
        sellPlantPage.clickSell();
    }


    /* ================= Verification Steps ================= */

    @Then("Sale should be created successfully")
    public void verifySaleCreatedSuccessfully() {
        // Wait for redirect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're back on sales page
        String currentUrl = sellPlantPage.getDriver().getCurrentUrl();
        System.out.println("After sale - Current URL: " + currentUrl);
        
        assertThat(currentUrl)
                .as("Should redirect to sales list after successful sale")
                .contains("/ui/sales");
    }
}
