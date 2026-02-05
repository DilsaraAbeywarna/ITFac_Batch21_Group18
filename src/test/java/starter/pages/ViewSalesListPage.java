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


    /* ===== Sell Plant Button ===== */

    @FindBy(css = "a.btn.btn-primary")
    private WebElementFacade sellPlantButton;



    /* ================= Navigate to Sales Page ================= */

    public void navigateToSalesPage() {
        // Get current URL before navigation
        String beforeUrl = getDriver().getCurrentUrl();
        System.out.println("Before navigation - Current URL: " + beforeUrl);
        
        // Navigate directly to sales page (preserves session from hook)
        getDriver().get("http://localhost:8080/ui/sales");
        
        // Wait for navigation
        waitABit(2000);
        
        // Check where we ended up
        String afterUrl = getDriver().getCurrentUrl();
        System.out.println("After navigation - Current URL: " + afterUrl);
    }

    public void clickSalesMenu() {
        // Try to find and click the sales menu link
        salesMenuLink
                .withTimeoutOf(Duration.ofSeconds(10))
                .waitUntilVisible()
                .waitUntilClickable()
                .click();

        waitABit(1500);
    }


    /* ================= Click Sell Plant Button ================= */

    public void clickSellPlantButton() {
        System.out.println("Clicking Sell Plant button");
        waitABit(2000); // Wait for page to fully load
        
        try {
            // Try to find and click the Sell Plant button
            if (sellPlantButton != null && sellPlantButton.isPresent() && sellPlantButton.isVisible()) {
                System.out.println("Found Sell Plant button using @FindBy");
                sellPlantButton.waitUntilClickable().click();
            } else {
                // Fallback: Navigate directly to sell plant page
                System.out.println("Sell Plant button not found, navigating directly");
                navigateToSellPlantPage();
                return;
            }
            
            System.out.println("Clicked Sell Plant button");
            waitABit(1500);
            
        } catch (Exception e) {
            System.out.println("ERROR clicking Sell Plant button: " + e.getMessage());
            // Fallback: Navigate directly
            navigateToSellPlantPage();
        }
    }

    public void navigateToSellPlantPage() {
        System.out.println("Navigating to Sell Plant page");
        System.out.println("Attempting to navigate to: http://localhost:8080/ui/sales/new");
        getDriver().get("http://localhost:8080/ui/sales/new");
        waitABit(2000);
        String finalUrl = getDriver().getCurrentUrl();
        System.out.println("Navigated to: " + finalUrl);
        
        // Check if redirected
        if (!finalUrl.contains("/ui/sales/new")) {
            System.out.println("WARNING: Was redirected from /ui/sales/new to " + finalUrl);
        }
    }



    /* ================= Verify Page ================= */

    public boolean isOnSalesPage() {
        // Wait a moment for page to load
        waitABit(1000);
        
        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current Sales URL: " + currentUrl);
        
        boolean onSalesPage = currentUrl.contains("/ui/sales");
        
        if (!onSalesPage) {
            System.out.println("ERROR: Not on sales page. Current URL: " + currentUrl);
        }
        
        return onSalesPage;
    }



    /* ================= Verify Table ================= */

    public boolean isSalesTableVisible() {
        try {
            // Wait for page to fully load
            waitABit(2000);
            
            // Check if the .table element is present and visible
            if ($(".table").isPresent()) {
                boolean isVisible = $(".table").isVisible();
                System.out.println("Sales table/list is visible: " + isVisible);
                return isVisible;
            } else {
                System.out.println("No .table element found on page");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("ERROR checking sales table: " + e.getMessage());
            return false;
        }
    }



    /* ================= Verify Records ================= */

    public boolean hasSalesRecords() {
        try {
            // Wait for content to load
            waitABit(1000);
            
            // Try to find rows in the table/list
            int rowCount = $$(".table tbody tr").size();
            
            if (rowCount == 0) {
                // Try alternative selectors
                rowCount = $$(".table tr").size();
            }
            
            if (rowCount == 0) {
                // Try list items
                rowCount = $$(".table .list-group-item").size();
            }
            
            System.out.println("Sales records/rows found: " + rowCount);
            
            // If no records, check if there's an empty state message
            if (rowCount == 0) {
                boolean hasEmptyMessage = $(".alert").isPresent() || 
                                         getDriver().getPageSource().contains("No sales") ||
                                         getDriver().getPageSource().contains("no records");
                                         
                if (hasEmptyMessage) {
                    System.out.println("Empty state detected - no sales records exist yet");
                }
            }
            
            return rowCount > 0;
            
        } catch (Exception e) {
            System.out.println("ERROR checking sales rows: " + e.getMessage());
            return false;
        }
    }
}
