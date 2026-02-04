package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;

@DefaultUrl("/ui/sales")
public class SalesPage extends PageObject {

    /* ===== Sell Plant Button ===== */
    @FindBy(css = "a[href='/ui/sales/add'], a.btn.btn-primary")
    private WebElementFacade sellPlantButton;


    /* ===== Cancel Button ===== */
    @FindBy(css = "button.btn.btn-secondary, a.btn.btn-secondary")
    private WebElementFacade cancelButton;


    /* ===== Open Sales Page ===== */
    public void openSalesPage() {

        getDriver().get("http://localhost:8080/ui/sales");

        System.out.println("Opened: " + getDriver().getCurrentUrl());
    }


    /* ===== Click Sell Plant ===== */
    public void clickSellPlant() {

        sellPlantButton
                .withTimeoutOf(Duration.ofSeconds(15))
                .waitUntilVisible()
                .waitUntilClickable()
                .click();

        waitABit(1500);
    }


    /* ===== Click Cancel ===== */
    public void clickCancel() {

        cancelButton
                .withTimeoutOf(Duration.ofSeconds(15))
                .waitUntilVisible()
                .waitUntilClickable()
                .click();

        waitABit(1500);
    }


    /* ===== Verify Redirect ===== */
    public boolean isOnSalesListPage() {

        waitForCondition()
                .withTimeout(Duration.ofSeconds(15))
                .until(driver ->
                        driver.getCurrentUrl().contains("/ui/sales")
                );

        System.out.println("Final URL: " + getDriver().getCurrentUrl());

        return getDriver()
                .getCurrentUrl()
                .contains("/ui/sales");
    }
}
