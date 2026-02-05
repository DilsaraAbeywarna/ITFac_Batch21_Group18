package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class NoSellRecords {

    @Steps
    ViewSalesListPage viewSalesListPage;

    @Then("{string} message should be displayed")
    public void verifyNoSalesMessage(String expectedMessage) {
        System.out.println("Verifying no sales message: " + expectedMessage);
        String actualMessage = viewSalesListPage.getNoSalesMessage();
        System.out.println("Found message: " + actualMessage);
        assertThat(actualMessage).isEqualToIgnoringCase(expectedMessage);
    }
}
