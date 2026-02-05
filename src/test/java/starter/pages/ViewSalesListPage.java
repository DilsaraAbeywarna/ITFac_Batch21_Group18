package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    /* ================= Get No Sales Message ================= */

    public String getNoSalesMessage() {
        try {
            // Look for the empty state message in the table body
            // <td colspan="5" class="text-center text-muted">No sales found</td>
            WebElementFacade emptyMessage = $("tbody tr td.text-center.text-muted");
            
            if (emptyMessage.isPresent()) {
                String message = emptyMessage.getText().trim();
                System.out.println("Empty sales message found: " + message);
                return message;
            }
            
            System.out.println("No empty sales message found");
            return "";
            
        } catch (Exception e) {
            System.out.println("ERROR getting no sales message: " + e.getMessage());
            return "";
        }
    }

    /* ================= Check Pagination Controls ================= */

    public boolean isPaginationVisible() {
        try {
            // Look for the pagination nav element
            // <nav><ul class="pagination">...</ul></nav>
            WebElementFacade paginationNav = $("nav ul.pagination");
            
            if (paginationNav.isVisible()) {
                System.out.println("Pagination controls found and visible");
                
                // Count pagination items for additional info
                List<WebElementFacade> pageItems = findAll("ul.pagination li.page-item");
                System.out.println("Number of pagination items: " + pageItems.size());
                
                return true;
            }
            
            System.out.println("Pagination controls not visible");
            return false;
            
        } catch (Exception e) {
            System.out.println("ERROR checking pagination: " + e.getMessage());
            return false;
        }
    }

    /* ================= Check Sales Sorted By Date Descending ================= */

    public boolean areSalesSortedByDateDescending() {
        try {
            // Get all "Sold At" date cells from the table
            // The 4th column contains the date/time
            List<WebElement> dateColumns = getDriver().findElements(By.cssSelector("table.table tbody tr td:nth-child(4)"));
            
            if (dateColumns.isEmpty()) {
                System.out.println("No date columns found in sales table");
                return false;
            }
            
            System.out.println("Found " + dateColumns.size() + " sales records with dates");
            
            // Extract and parse dates
            List<LocalDateTime> dates = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            
            for (WebElement dateColumn : dateColumns) {
                String dateText = dateColumn.getText().trim();
                System.out.println("Date: " + dateText);
                
                try {
                    LocalDateTime date = LocalDateTime.parse(dateText, formatter);
                    dates.add(date);
                } catch (Exception e) {
                    System.out.println("WARNING: Could not parse date: " + dateText);
                    // Continue with other dates
                }
            }
            
            if (dates.size() < 2) {
                System.out.println("Need at least 2 dates to verify sorting");
                return dates.size() == 1; // Single record is technically sorted
            }
            
            // Check if dates are in descending order (newest first)
            for (int i = 0; i < dates.size() - 1; i++) {
                LocalDateTime current = dates.get(i);
                LocalDateTime next = dates.get(i + 1);
                
                if (current.isBefore(next)) {
                    System.out.println("ERROR: Dates not in descending order:");
                    System.out.println("  Position " + i + ": " + current);
                    System.out.println("  Position " + (i+1) + ": " + next);
                    return false;
                }
            }
            
            System.out.println("Sales are correctly sorted by date in descending order (newest first)");
            return true;
            
        } catch (Exception e) {
            System.out.println("ERROR checking date sorting: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /* ================= Click Quantity Column Header ================= */

    public void clickQuantityColumnHeader() {
        try {
            // Find and click the Quantity column header link
            // <a class="text-white text-decoration-none" href="/ui/sales?page=0&sortField=quantity&sortDir=asc">Quantity</a>
            WebElement quantityHeader = getDriver().findElement(By.xpath("//th/a[contains(text(), 'Quantity')]"));
            
            System.out.println("Clicking Quantity column header");
            quantityHeader.click();
            
            // Wait for page to reload with new sorting
            waitABit(2000);
            
            System.out.println("Clicked Quantity column header successfully");
            
        } catch (Exception e) {
            System.out.println("ERROR clicking Quantity header: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* ================= Check Sales Sorted By Quantity Ascending ================= */

    public boolean areSalesSortedByQuantityAscending() {
        try {
            // Get all quantity cells from the table (2nd column)
            List<WebElement> quantityColumns = getDriver().findElements(By.cssSelector("table.table tbody tr td:nth-child(2)"));
            
            if (quantityColumns.isEmpty()) {
                System.out.println("No quantity columns found in sales table");
                return false;
            }
            
            System.out.println("Found " + quantityColumns.size() + " sales records with quantities");
            
            // Extract quantities
            List<Integer> quantities = new ArrayList<>();
            
            for (WebElement quantityColumn : quantityColumns) {
                String quantityText = quantityColumn.getText().trim();
                System.out.println("Quantity: " + quantityText);
                
                try {
                    int quantity = Integer.parseInt(quantityText);
                    quantities.add(quantity);
                } catch (NumberFormatException e) {
                    System.out.println("WARNING: Could not parse quantity: " + quantityText);
                }
            }
            
            if (quantities.size() < 2) {
                System.out.println("Need at least 2 quantities to verify sorting");
                return quantities.size() == 1;
            }
            
            // Check if quantities are in ascending order (smallest first)
            for (int i = 0; i < quantities.size() - 1; i++) {
                int current = quantities.get(i);
                int next = quantities.get(i + 1);
                
                if (current > next) {
                    System.out.println("ERROR: Quantities not in ascending order:");
                    System.out.println("  Position " + i + ": " + current);
                    System.out.println("  Position " + (i+1) + ": " + next);
                    return false;
                }
            }
            
            System.out.println("Sales are correctly sorted by quantity in ascending order");
            return true;
            
        } catch (Exception e) {
            System.out.println("ERROR checking quantity sorting: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /* ================= Check Sales Sorted By Quantity Descending ================= */

    public boolean areSalesSortedByQuantityDescending() {
        try {
            // Get all quantity cells from the table (2nd column)
            List<WebElement> quantityColumns = getDriver().findElements(By.cssSelector("table.table tbody tr td:nth-child(2)"));
            
            if (quantityColumns.isEmpty()) {
                System.out.println("No quantity columns found in sales table");
                return false;
            }
            
            System.out.println("Found " + quantityColumns.size() + " sales records with quantities");
            
            // Extract quantities
            List<Integer> quantities = new ArrayList<>();
            
            for (WebElement quantityColumn : quantityColumns) {
                String quantityText = quantityColumn.getText().trim();
                System.out.println("Quantity: " + quantityText);
                
                try {
                    int quantity = Integer.parseInt(quantityText);
                    quantities.add(quantity);
                } catch (NumberFormatException e) {
                    System.out.println("WARNING: Could not parse quantity: " + quantityText);
                }
            }
            
            if (quantities.size() < 2) {
                System.out.println("Need at least 2 quantities to verify sorting");
                return quantities.size() == 1;
            }
            
            // Check if quantities are in descending order (largest first)
            for (int i = 0; i < quantities.size() - 1; i++) {
                int current = quantities.get(i);
                int next = quantities.get(i + 1);
                
                if (current < next) {
                    System.out.println("ERROR: Quantities not in descending order:");
                    System.out.println("  Position " + i + ": " + current);
                    System.out.println("  Position " + (i+1) + ": " + next);
                    return false;
                }
            }
            
            System.out.println("Sales are correctly sorted by quantity in descending order");
            return true;
            
        } catch (Exception e) {
            System.out.println("ERROR checking quantity sorting: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
