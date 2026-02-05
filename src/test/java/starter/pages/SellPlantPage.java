package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.time.Duration;

@DefaultUrl("page:ui/sales")
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
        
        // Check URL - accept both /new and /sell in case of redirects
        boolean urlCorrect = currentUrl.contains("/ui/sales/new") || currentUrl.contains("/ui/sales/sell");
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


    /* ================= Fill Form ================= */

    public void selectPlant(String plantName) {
        System.out.println("Selecting plant: " + plantName);
        waitABit(1000);
        
        try {
            // Use $() selector instead of @FindBy
            WebElementFacade dropdown = $("#plantId");
            dropdown.waitUntilVisible().waitUntilEnabled();
            
            // Map plant names to values based on HTML
            String value = "1"; // Default to Rose
            if (plantName.equalsIgnoreCase("Rose")) {
                value = "1";
            } else if (plantName.equalsIgnoreCase("Lily")) {
                value = "2";
            }
            
            dropdown.selectByValue(value);
            
            System.out.println("Selected plant: " + plantName + " (value: " + value + ")");
            waitABit(500);
        } catch (Exception e) {
            System.out.println("ERROR selecting plant: " + e.getMessage());
            throw e;
        }
    }

    public void enterQuantity(int quantity) {
        System.out.println("Entering quantity: " + quantity);
        waitABit(500);
        
        try {
            // Use $() selector instead of @FindBy
            WebElementFacade quantityField = $("#quantity");
            quantityField.waitUntilVisible().waitUntilEnabled();
            quantityField.clear();
            quantityField.type(String.valueOf(quantity));
            
            System.out.println("Entered quantity: " + quantity);
            waitABit(500);
        } catch (Exception e) {
            System.out.println("ERROR entering quantity: " + e.getMessage());
            throw e;
        }
    }

    public void clickSell() {
        System.out.println("Clicking Sell button");
        waitABit(1000);
        
        try {
            // Use CSS selector with By.cssSelector to ensure correct selector type
            WebElementFacade sellBtn = find(By.cssSelector("button.btn.btn-primary"));
            sellBtn.waitUntilVisible().waitUntilEnabled().waitUntilClickable();
            sellBtn.click();
            
            System.out.println("Clicked Sell button");
            waitABit(2000); // Wait for submission and redirect
        } catch (Exception e) {
            System.out.println("ERROR clicking sell button: " + e.getMessage());
            throw e;
        }
    }
}
