package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import starter.pages.SellPlantPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidateQuantitySteps {

    @Steps
    SellPlantPage sellPlantPage;

    @Then("Validation message {string} should be displayed")
    public void verifyValidationMessage(String expectedMessage) {
        System.out.println("Verifying validation message: " + expectedMessage);
        String actualMessage = sellPlantPage.getQuantityValidationMessage();
        System.out.println("Actual validation message: " + actualMessage);
        assertThat(actualMessage)
                .as("Validation message should be displayed")
                .isEqualTo(expectedMessage);
    }
}
