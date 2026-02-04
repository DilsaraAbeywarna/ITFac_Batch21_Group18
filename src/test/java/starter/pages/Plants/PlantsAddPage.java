package starter.pages.Plants;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;

@DefaultUrl("/ui/plants")
public class PlantsAddPage extends PageObject {

    // Sidebar navigation link to Plants page
    @FindBy(css = "a[href='/ui/plants']")
    public WebElementFacade plantsNavLink;

    // Add Plant button
    @FindBy(css = "a[href='/ui/plants/add'].btn.btn-primary")
    public WebElementFacade addPlantButton;

    // Alternative locator for Add Plant button (if needed)
    @FindBy(xpath = "//a[@href='/ui/plants/add' and contains(@class, 'btn-primary')]")
    public WebElementFacade addPlantButtonAlt;

    // Page heading/title
    @FindBy(css = "h1, h2")
    public WebElementFacade pageHeading;

    // Plant list table/container
    @FindBy(css = "table, .plant-list, .table")
    public WebElementFacade plantListContainer;

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/plants");
        System.out.println("Navigated to: " + getDriver().getCurrentUrl());
    }

    public void navigateToPlantsPageViaSidebar() {
        plantsNavLink.waitUntilClickable().click();
        waitABit(1000);
        System.out.println("Clicked Plants sidebar link. Current URL: " + getDriver().getCurrentUrl());
    }

    public boolean isPlantListPageDisplayed() {
        try {
            // Check URL
            boolean urlCorrect = getDriver().getCurrentUrl().contains("/ui/plants") 
                              && !getDriver().getCurrentUrl().contains("/ui/plants/add");
            
            // Check page heading is visible (if exists)
            boolean headingVisible = pageHeading.isVisible();
            
            System.out.println("Plant list page displayed - URL correct: " + urlCorrect 
                             + ", Heading visible: " + headingVisible);
            
            return urlCorrect && headingVisible;
        } catch (Exception e) {
            System.out.println("Error checking plant list page: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddPlantButtonVisible() {
        try {
            addPlantButton.waitUntilVisible();
            boolean isVisible = addPlantButton.isVisible();
            System.out.println("Add Plant button visibility: " + isVisible);
            return isVisible;
        } catch (Exception e) {
            System.out.println("Add Plant button not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddPlantButtonEnabled() {
        try {
            boolean isEnabled = addPlantButton.isEnabled();
            System.out.println("Add Plant button enabled: " + isEnabled);
            return isEnabled;
        } catch (Exception e) {
            System.out.println("Error checking if button is enabled: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddPlantButtonClickable() {
        try {
            addPlantButton.waitUntilClickable();
            boolean isClickable = addPlantButton.isClickable();
            System.out.println("Add Plant button clickable: " + isClickable);
            return isClickable;
        } catch (Exception e) {
            System.out.println("Add Plant button not clickable: " + e.getMessage());
            return false;
        }
    }

    public boolean isOnPlantsListPage() {
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(driver -> {
                    String currentUrl = driver.getCurrentUrl();
                    System.out.println("Checking URL: " + currentUrl);
                    return currentUrl.contains("/ui/plants");
                });

        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        return currentUrl.contains("/ui/plants") && !currentUrl.contains("/ui/plants/add");
    }

    public String getAddPlantButtonText() {
        return addPlantButton.getText();
    }
    
    public String getPageHeading() {
        return pageHeading.getText();
    }
}