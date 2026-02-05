package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.time.Duration;

@DefaultUrl("/ui/sales/sell")
public class SellPlantPage extends PageObject {

    /* ===== Form Elements ===== */

    @FindBy(id = "plantId")
    private WebElementFacade plantDropdown;

    @FindBy(id = "quantity")
    private WebElementFacade quantityInput;

    @FindBy(css = "button.btn.btn-primary")
    private WebElementFacade sellButton;

    @FindBy(css = "a.btn-secondary")
    private WebElementFacade cancelButton;


    /* ================= Verify Page ================= */

    public boolean isOnSellPlantPage() {
        // Wait for page to load
        waitABit(1000);
        
        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        
        // Check URL and page title
        boolean urlCorrect = currentUrl.contains("/ui/sales/sell") || currentUrl.contains("/ui/sales/new");
        boolean titleCorrect = getDriver().getTitle().contains("Sell Plant") || 
                              getDriver().getPageSource().contains("Sell Plant");
        
        System.out.println("On Sell Plant page: " + (urlCorrect || titleCorrect));
        return urlCorrect || titleCorrect;
    }


    /* ================= Click Cancel ================= */

    public void clickCancel() {
        System.out.println("Clicking Cancel button");
        waitABit(2000); // Wait for page to fully load
        try {
            // Use By.cssSelector explicitly
            find(By.cssSelector("a.btn-secondary")).waitUntilClickable().click();
            System.out.println("Clicked Cancel button");
            waitABit(1500);
        } catch (Exception e) {
            System.out.println("ERROR clicking cancel: " + e.getMessage());
            throw e;
        }
    }


    /* ================= Fill Form (for future tests) ================= */

    public void selectPlant(String plantName) {
        plantDropdown.selectByVisibleText(plantName);
    }

    public void enterQuantity(int quantity) {
        quantityInput.clear();
        quantityInput.type(String.valueOf(quantity));
    }

    public void clickSell() {
        sellButton.click();
        waitABit(1000);
    }
}
