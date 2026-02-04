package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;
import java.util.List;

@DefaultUrl("/ui/sales")
public class ViewSalesListPage extends PageObject {

    /* ===== Sales Menu (Sidebar) ===== */

    @FindBy(css = "a[href='/ui/sales']")
    private WebElementFacade salesMenuLink;


    /* ===== Sales Table ===== */

    @FindBy(css = "table.table")
    private WebElementFacade salesTable;


    /* ===== Sales Rows ===== */

    @FindBy(css = "table.table tbody tr")
    private List<WebElementFacade> salesRows;



    /* ================= Click Sales ================= */

    public void clickSalesMenu() {

        salesMenuLink
                .withTimeoutOf(Duration.ofSeconds(15))
                .waitUntilVisible()
                .waitUntilClickable()
                .click();

        waitABit(1500);
    }



    /* ================= Verify Page ================= */

    public boolean isOnSalesPage() {

        waitForCondition()
                .withTimeout(Duration.ofSeconds(15))
                .until(driver ->
                        driver.getCurrentUrl().contains("/ui/sales")
                );

        System.out.println("Sales URL: " + getDriver().getCurrentUrl());

        return getDriver()
                .getCurrentUrl()
                .contains("/ui/sales");
    }



    /* ================= Verify Table ================= */

    public boolean isSalesTableVisible() {

        return salesTable
                .withTimeoutOf(Duration.ofSeconds(15))
                .waitUntilVisible()
                .isDisplayed();
    }



    /* ================= Verify Records ================= */

    public boolean hasSalesRecords() {

        waitForCondition()
                .withTimeout(Duration.ofSeconds(15))
                .until(driver -> salesRows.size() > 0);

        System.out.println("Sales rows count: " + salesRows.size());

        return salesRows.size() > 0;
    }
}
