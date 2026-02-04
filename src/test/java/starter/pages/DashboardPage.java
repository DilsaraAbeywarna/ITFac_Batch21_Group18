package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.interactions.Actions;

@DefaultUrl("/ui/dashboard")
public class DashboardPage extends PageObject {

    @FindBy(css = ".sidebar.nav.flex-column")
    public WebElementFacade sideBar;

    @FindBy(css = ".header h2")
    public WebElementFacade header;

    @FindBy(css = ".sidebar .nav-link")
    public List<WebElementFacade> sidebarMenus;

    @FindBy(css = ".dashboard-card")
    public List<WebElementFacade> dashboardCards;

    private WebElementFacade hoveredMenu;

    private WebElementFacade hoveredCard;

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/dashboard");
        System.out.println("Explicitly navigated to: " + getDriver().getCurrentUrl());
    }

    public void isHeaderVisibleWithText(String text) {
        header.shouldBeVisible();
        header.shouldContainText(text);
    }

    public void isSideMenuVisibleWithTexts(List<String> menuTexts) {
        sideBar.shouldBeVisible();
        List<String> actualMenuTexts = sidebarMenus.stream()
                .map(menu -> menu.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();

        System.out.println("Sidebar menu texts: " + actualMenuTexts);
        for (int i = 0; i < menuTexts.size(); i++) {
            actualMenuTexts.contains(actualMenuTexts.get(i));
        }
    }

    public void areDashboardCardsVisible(int expectedCount) {
        if (dashboardCards.size() != expectedCount) {
            throw new AssertionError(
                    "Expected " + expectedCount + " dashboard cards, but found " + dashboardCards.size());
        }
        for (WebElementFacade card : dashboardCards) {
            card.shouldBeVisible();
        }
    }

    public void hoverOnSideMenu() {
        hoveredMenu = sidebarMenus.get(1);
    }

    public void verifyHoveredMenuBackgroundColor(String expectedHexColor) {
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
        hoveredCard = dashboardCards.get(0);
    }

    public void verifyDashboardCardMotion() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(hoveredCard).perform();

        waitABit(500);

        String transformValue = hoveredCard.getCssValue("transform");
        System.out.println("Hovered card transform value: " + transformValue);

        if (transformValue == null || !transformValue.contains("matrix")) {
            throw new AssertionError(
                    "Expected card to show motion on hover, but transform value was: " + transformValue);
        }
    }
    
    

}
