package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("/ui/dashboard")
public class DashboardPage extends PageObject {

    @FindBy(css = "div.sidebar")
    private WebElementFacade sideBar;

    @FindBy(css = ".header h2")
    private WebElementFacade header;

    @FindBy(css = ".sidebar a.nav-link")
    private List<WebElementFacade> sidebarMenus;

    @FindBy(css = ".dashboard-card")
    private List<WebElementFacade> dashboardCards;

    private WebElementFacade hoveredMenu;
    private WebElementFacade hoveredCard;

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/dashboard");
        System.out.println("Explicitly navigated to: " + getDriver().getCurrentUrl());
        waitForPageToLoad();
    }

    // ✅ Add this method to ensure page is fully loaded
    private void waitForPageToLoad() {
        // Wait for sidebar to be present
        waitFor(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sidebar")));

        // Wait for at least one menu link
        waitFor(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sidebar a.nav-link")));

        // Small buffer for JavaScript to finish
        waitABit(500);
    }

    // ✅ Add helper method to safely get sidebar menus
    private List<WebElementFacade> getSidebarMenus() {
        // Re-find elements to avoid stale element issues
        if (sidebarMenus == null || sidebarMenus.isEmpty()) {
            System.out.println("⚠️ sidebarMenus is null/empty, re-finding elements...");
            waitForPageToLoad();
            // Force re-initialization by finding elements directly
            sidebarMenus = findAll(".sidebar a.nav-link");
        }

        // Double-check they're still valid
        try {
            if (!sidebarMenus.isEmpty()) {
                sidebarMenus.get(0).isVisible(); // Test if elements are still valid
            }
        } catch (Exception e) {
            System.out.println("⚠️ Stale elements detected, re-finding...");
            sidebarMenus = findAll(".sidebar a.nav-link");
        }

        return sidebarMenus;
    }

    /**
     * Verify header is visible with expected text
     */
    public void isHeaderVisibleWithText(String text) {
        System.out.println("🔍 Verifying header with text: '" + text + "'");

        waitForPageToLoad();

        // Find header element directly instead of using @FindBy
        WebElementFacade headerElement = find(By.cssSelector(".header h2"));

        headerElement.waitUntilVisible();
        headerElement.shouldBeVisible();

        String actualHeaderText = headerElement.getText().trim();
        System.out.println("📋 Actual header text: '" + actualHeaderText + "'");

        if (!actualHeaderText.contains(text)) {
            throw new AssertionError(
                    String.format("Expected header to contain '%s' but found '%s'", text, actualHeaderText));
        }

        System.out.println("✓ Header verified successfully");
    }

    public void isSideMenuVisibleWithTexts(List<String> menuTexts) {
        System.out.println("=== Verifying sidebar menu texts ===");
        System.out.println("header is " + header);
        System.out.println("sideBar is " + sideBar);

        waitForPageToLoad();

        // ✅ Use the safe getter method
        List<WebElementFacade> menus = getSidebarMenus();

        System.out.println("Sidebar menus found: " + menus.size());

        if (menus.isEmpty()) {
            throw new AssertionError(
                    "No sidebar menu items found! Current URL: " + getDriver().getCurrentUrl());
        }

        List<String> actualMenuTexts = menus.stream()
                .map(menu -> menu.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();

        System.out.println("Expected menu texts: " + menuTexts);
        System.out.println("Actual menu texts: " + actualMenuTexts);

        for (String expectedText : menuTexts) {
            if (!actualMenuTexts.contains(expectedText)) {
                throw new AssertionError(
                        "Expected menu text '" + expectedText + "' not found in sidebar. " +
                                "Actual menu texts: " + actualMenuTexts);
            }
        }

        System.out.println("✓ All menu texts verified successfully");
    }

    public void areDashboardCardsVisible(int expectedCount) {
        waitForPageToLoad();

        // Re-find cards if needed
        if (dashboardCards == null || dashboardCards.isEmpty()) {
            dashboardCards = findAll(".dashboard-card");
        }

        if (dashboardCards.size() != expectedCount) {
            throw new AssertionError(
                    "Expected " + expectedCount + " dashboard cards, but found " + dashboardCards.size());
        }
        for (WebElementFacade card : dashboardCards) {
            card.shouldBeVisible();
        }
    }

    public void hoverOnSideMenu() {
        waitForPageToLoad();

        // ✅ Use safe getter
        List<WebElementFacade> menus = getSidebarMenus();

        if (menus.size() < 2) {
            throw new AssertionError("Not enough menu items found to hover over the second one");
        }

        hoveredMenu = menus.get(1);
        System.out.println("Selected menu item for hover: " + hoveredMenu.getText());
    }

    public void verifyHoveredMenuBackgroundColor(String expectedHexColor) {
        if (hoveredMenu == null) {
            throw new AssertionError("No menu item selected for hover. Call hoverOnSideMenu() first!");
        }

        Actions actions = new Actions(getDriver());
        actions.moveToElement(hoveredMenu).perform();

        waitABit(500);

        String expectedRgba = hexToRgba(expectedHexColor);

        waitForCondition().until(
                driver -> hoveredMenu.getCssValue("background-color") != null);

        String actualColor = hoveredMenu.getCssValue("background-color");
        System.out.println("Hovered menu background color: " + actualColor);
        System.out.println("Expected RGBA color: " + expectedRgba);

        if (!actualColor.equals(expectedRgba)) {
            throw new AssertionError(
                    "Expected background color " + expectedRgba +
                            " but found " + actualColor);
        }
    }

    private String hexToRgba(String hex) {
        Map<String, String> hexToRgbaMap = Map.of(
                "#374151", "rgba(55, 65, 81, 1)");

        if (!hexToRgbaMap.containsKey(hex)) {
            throw new IllegalArgumentException(
                    "Unsupported color: " + hex);
        }
        return hexToRgbaMap.get(hex);
    }

    public void hoverOnDashboardCard() {
        waitForPageToLoad();

        // Re-find cards if needed
        if (dashboardCards == null || dashboardCards.isEmpty()) {
            dashboardCards = findAll(".dashboard-card");
        }

        if (dashboardCards.isEmpty()) {
            throw new AssertionError("No dashboard cards found!");
        }

        hoveredCard = dashboardCards.get(0);
        hoveredCard.waitUntilVisible();

        String originalTransform = hoveredCard.getCssValue("transform");
        System.out.println("Card transform BEFORE hover: " + originalTransform);
    }

    public void verifyDashboardCardMotion() {
        if (hoveredCard == null) {
            throw new AssertionError("No card was selected. Call hoverOnDashboardCard() first!");
        }

        Actions actions = new Actions(getDriver());
        actions.moveToElement(hoveredCard).perform();

        waitABit(1000);

        String transformValue = hoveredCard.getCssValue("transform");
        System.out.println("Card transform AFTER hover: " + transformValue);

        if (transformValue == null || transformValue.equals("none")) {
            throw new AssertionError(
                    "Expected card to show motion on hover, but transform value was: " + transformValue);
        }

        if (!transformValue.contains("matrix")) {
            throw new AssertionError(
                    "Expected matrix transform on hover, but got: " + transformValue);
        }

        System.out.println("✓ Card motion verified: " + transformValue);
    }

    public void clickTheMangeCategoriesButtonInCategoryCard() {
        waitForPageToLoad();

        System.out.println("Attempting to click Manage Categories button...");
        System.out.println("Current URL before click: " + getDriver().getCurrentUrl());

        // Try to find the link directly by href attribute
        WebElementFacade manageCategoriesLink = find(By.cssSelector("a[href='/ui/categories'], a[href*='categories']"));

        if (manageCategoriesLink != null && manageCategoriesLink.isPresent()) {
            System.out.println("Found Manage Categories link directly");
            manageCategoriesLink.waitUntilClickable();
            manageCategoriesLink.click();
        } else {
            // Fallback: Look inside dashboard cards
            System.out.println("Link not found directly, searching in dashboard cards...");

            if (dashboardCards == null || dashboardCards.isEmpty()) {
                dashboardCards = findAll(".dashboard-card");
            }

            System.out.println("Dashboard cards found: " + dashboardCards.size());

            WebElementFacade categoriesCard = dashboardCards.stream()
                    .filter(card -> card.containsText("Manage Categories") || card.containsText("Categories"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Categories dashboard card not found"));

            System.out.println("Categories card found with text: " + categoriesCard.getText());

            // Try to find link inside the card
            WebElementFacade linkInsideCard = categoriesCard.findBy("a");

            if (linkInsideCard != null && linkInsideCard.isVisible()) {
                System.out.println("Found link inside card, href: " + linkInsideCard.getAttribute("href"));
                linkInsideCard.waitUntilClickable();
                linkInsideCard.click();
            } else {
                System.out.println("No link found, clicking the card itself");
                categoriesCard.waitUntilClickable();
                categoriesCard.click();
            }
        }

        // Wait for navigation to complete
        waitABit(2000);

        System.out.println("After click - Current URL: " + getDriver().getCurrentUrl());
    }

    /**
     * Verify the Main category count on the Categories card
     */
    public void verifyMainCategoryCount(int expectedCount) {
        waitABit(1000);

        System.out.println("🔍 Verifying Main category count on dashboard...");

        // Find the Categories card
        WebElementFacade categoriesCard = find(
                By.xpath(
                        "//div[contains(@class, 'card')]//h6[contains(text(), 'Categories')]/ancestor::div[contains(@class, 'card')]"));

        categoriesCard.waitUntilVisible();

        // Find the Main categories count
        WebElementFacade mainCountElement = categoriesCard.findBy(
                By.xpath(".//div[contains(@class, 'd-flex')]//div[contains(., 'Main')]/../div[@class='fw-bold fs-5']"));

        String mainCountText = mainCountElement.getText().trim();
        int actualMainCount = Integer.parseInt(mainCountText);

        System.out.println("📊 Main categories count: " + actualMainCount);

        if (actualMainCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d main categories but found %d on dashboard",
                            expectedCount, actualMainCount));
        }

        System.out.println("✓ Main category count verified: " + actualMainCount);
    }

    /**
     * Verify the Sub category count on the Categories card
     */
    public void verifySubCategoryCount(int expectedCount) {
        waitABit(500);

        System.out.println("🔍 Verifying Sub category count on dashboard...");

        // Find the Categories card
        WebElementFacade categoriesCard = find(
                By.xpath(
                        "//div[contains(@class, 'card')]//h6[contains(text(), 'Categories')]/ancestor::div[contains(@class, 'card')]"));

        categoriesCard.waitUntilVisible();

        // Find the Sub categories count
        WebElementFacade subCountElement = categoriesCard.findBy(
                By.xpath(".//div[contains(@class, 'd-flex')]//div[contains(., 'Sub')]/../div[@class='fw-bold fs-5']"));

        String subCountText = subCountElement.getText().trim();
        int actualSubCount = Integer.parseInt(subCountText);

        System.out.println("📊 Sub categories count: " + actualSubCount);

        if (actualSubCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d sub categories but found %d on dashboard",
                            expectedCount, actualSubCount));
        }

        System.out.println("✓ Sub category count verified: " + actualSubCount);
    }

    /**
     * Verify both Main and Sub category counts together
     */
    public void verifyCategoryCounts(int expectedMainCount, int expectedSubCount) {
        waitABit(1000);

        System.out.println("🔍 Verifying category counts on dashboard...");

        // Find the Categories card
        WebElementFacade categoriesCard = find(
                By.xpath("//div[contains(@class, 'card-body')][.//h6[contains(text(), 'Categories')]]"));

        categoriesCard.waitUntilVisible();

        // Find all count elements with class "fw-bold fs-5"
        List<WebElementFacade> countElements = categoriesCard.thenFindAll("div.fw-bold.fs-5");

        if (countElements.size() < 2) {
            throw new AssertionError(
                    "Expected to find 2 count elements (Main and Sub) but found " + countElements.size());
        }

        // First element is Main count
        int actualMainCount = Integer.parseInt(countElements.get(0).getText().trim());
        System.out.println("📊 Main categories: " + actualMainCount);

        // Second element is Sub count
        int actualSubCount = Integer.parseInt(countElements.get(1).getText().trim());
        System.out.println("📊 Sub categories: " + actualSubCount);

        // Verify Main count
        if (actualMainCount != expectedMainCount) {
            throw new AssertionError(
                    String.format("Expected %d main categories but found %d",
                            expectedMainCount, actualMainCount));
        }

        // Verify Sub count
        if (actualSubCount != expectedSubCount) {
            throw new AssertionError(
                    String.format("Expected %d sub categories but found %d",
                            expectedSubCount, actualSubCount));
        }

        System.out.println("✓ Category counts verified - Main: " + actualMainCount + ", Sub: " + actualSubCount);
    }

    /**
     * Verify Total Plants count on Plants card
     */
    public void verifyTotalPlantsCount(int expectedCount) {
        waitABit(1000);

        System.out.println("🔍 Verifying Total Plants count on dashboard...");

        // Find the Plants card
        WebElementFacade plantsCard = find(
                By.xpath("//div[contains(@class, 'card-body')][.//h6[text()='Plants']]"));

        plantsCard.waitUntilVisible();

        // Find the Total plants count
        WebElementFacade totalCountElement = plantsCard.findBy(
                By.xpath(".//div[contains(text(), 'Total')]/../div[@class='fw-bold fs-5']"));

        String totalCountText = totalCountElement.getText().trim();
        int actualCount = Integer.parseInt(totalCountText);

        System.out.println("📊 Total Plants: " + actualCount);

        if (actualCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d total plants but found %d on dashboard",
                            expectedCount, actualCount));
        }

        System.out.println("✓ Total plants count verified: " + actualCount);
    }

    /**
     * Verify Low Stock Plants count on Plants card
     */
    public void verifyLowStockPlantsCount(int expectedCount) {
        waitABit(500);

        System.out.println("🔍 Verifying Low Stock Plants count on dashboard...");

        // Find the Plants card
        WebElementFacade plantsCard = find(
                By.xpath("//div[contains(@class, 'card-body')][.//h6[text()='Plants']]"));

        plantsCard.waitUntilVisible();

        // Find the Low Stock plants count
        WebElementFacade lowStockElement = plantsCard.findBy(
                By.xpath(".//div[contains(text(), 'Low Stock')]/../div[@class='fw-bold fs-5']"));

        String lowStockText = lowStockElement.getText().trim();
        int actualCount = Integer.parseInt(lowStockText);

        System.out.println("📊 Low Stock Plants: " + actualCount);

        if (actualCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d low stock plants but found %d on dashboard",
                            expectedCount, actualCount));
        }

        System.out.println("✓ Low stock plants count verified: " + actualCount);
    }

    /**
     * Verify Revenue amount on Sales card
     */
    public void verifyRevenueAmount(String expectedRevenue) {
        waitABit(1000);

        System.out.println("🔍 Verifying Revenue amount on dashboard...");

        // Find the Sales card
        WebElementFacade salesCard = find(
                By.xpath("//div[contains(@class, 'card-body')][.//h6[text()='Sales']]"));

        salesCard.waitUntilVisible();

        // Find the Revenue element (contains "Rs" and a span)
        WebElementFacade revenueElement = salesCard.findBy(
                By.xpath(".//div[contains(text(), 'Revenue')]/../div[@class='fw-bold fs-5']"));

        String actualRevenue = revenueElement.getText().trim();

        System.out.println("💰 Revenue: " + actualRevenue);

        if (!actualRevenue.equals(expectedRevenue)) {
            throw new AssertionError(
                    String.format("Expected revenue '%s' but found '%s' on dashboard",
                            expectedRevenue, actualRevenue));
        }

        System.out.println("✓ Revenue verified: " + actualRevenue);
    }

    /**
     * Verify Sales count on Sales card
     */
    public void verifySalesCount(int expectedCount) {
        waitABit(500);

        System.out.println("🔍 Verifying Sales count on dashboard...");

        // Find the Sales card
        WebElementFacade salesCard = find(
                By.xpath("//div[contains(@class, 'card-body')][.//h6[text()='Sales']]"));

        salesCard.waitUntilVisible();

        // Find the Sales count
        WebElementFacade salesCountElement = salesCard.findBy(
                By.xpath(".//div[contains(text(), 'Sales')]/../div[@class='fw-bold fs-5']"));

        String salesCountText = salesCountElement.getText().trim();
        int actualCount = Integer.parseInt(salesCountText);

        System.out.println("📊 Sales Count: " + actualCount);

        if (actualCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d sales but found %d on dashboard",
                            expectedCount, actualCount));
        }

        System.out.println("✓ Sales count verified: " + actualCount);
    }

}
