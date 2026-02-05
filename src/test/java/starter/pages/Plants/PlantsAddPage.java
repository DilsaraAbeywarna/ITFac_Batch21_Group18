package starter.pages.Plants;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;
import java.util.List;

@DefaultUrl("/ui/plants")
public class PlantsAddPage extends PageObject {

    // Navigation link
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

    // Add Plant form fields
    @FindBy(id = "name")
    public WebElementFacade plantNameInput;

    @FindBy(id = "categoryId")
    public WebElementFacade categoryDropdown;

    @FindBy(id = "price")
    public WebElementFacade priceInput;

    @FindBy(id = "quantity")
    public WebElementFacade quantityInput;

    @FindBy(css = "button.btn.btn-primary")
    public WebElementFacade saveButton;

    @FindBy(css = ".alert-success, .alert[role='alert']")
    public WebElementFacade successMessage;

    // Validation error for plant name
    @FindBy(xpath = "//div[contains(@class,'text-danger') and contains(text(),'Plant name must be between 3 and 25 characters')]")
    public WebElementFacade nameValidationError;

    // Validation error for price
    @FindBy(xpath = "//div[contains(@class,'text-danger') and contains(text(),'Price must be greater than 0')]")
    public WebElementFacade priceValidationError;

    // Low stock badge elements
    @FindBy(xpath = "//table//tbody//tr")
    public List<WebElementFacade> plantTableRows;

    @FindBy(xpath = "//table//tbody//tr[contains(., 'Lotus')]")
    public WebElementFacade plantRowWithQuantity3;

    @FindBy(xpath = "//span[@class='badge bg-danger ms-2' and contains(text(), 'Low')]")
    public WebElementFacade lowStockBadge;

    // ==================== Validation Methods ====================
    
    public boolean isNameValidationErrorDisplayed(String expectedMessage) {
        try {
            nameValidationError.waitUntilVisible();
            boolean visible = nameValidationError.isVisible();
            String text = nameValidationError.getText();
            boolean correctText = text.contains(expectedMessage);
            String color = nameValidationError.getCssValue("color");
            boolean isRed = false;
            if (color != null) {
                String c = color.toLowerCase();
                isRed = c.contains("255, 0, 0") || c.contains("red") || c.contains("220, 53, 69");
            }
            if (!(visible && correctText && isRed)) {
                System.out.println("[DEBUG] Validation error visible: " + visible);
                System.out.println("[DEBUG] Validation error text: '" + text + "'");
                System.out.println("[DEBUG] Validation error color: " + color);
            }
            return visible && correctText && isRed;
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception in isNameValidationErrorDisplayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isPriceValidationErrorDisplayed(String expectedMessage) {
        try {
            priceValidationError.waitUntilVisible();
            boolean visible = priceValidationError.isVisible();
            String text = priceValidationError.getText();
            boolean correctText = text.contains(expectedMessage);
            String color = priceValidationError.getCssValue("color");
            boolean isRed = false;
            if (color != null) {
                String c = color.toLowerCase();
                isRed = c.contains("255, 0, 0") || c.contains("red") || c.contains("220, 53, 69");
            }
            if (!(visible && correctText && isRed)) {
                System.out.println("[DEBUG] Price validation error visible: " + visible);
                System.out.println("[DEBUG] Price validation error text: '" + text + "'");
                System.out.println("[DEBUG] Price validation error color: " + color);
            }
            return visible && correctText && isRed;
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception in isPriceValidationErrorDisplayed: " + e.getMessage());
            return false;
        }
    }

    // ==================== Navigation Methods ====================
    
    public void openPage() {
        getDriver().get("http://localhost:8080/ui/plants");
        System.out.println("Navigated to: " + getDriver().getCurrentUrl());
    }

    public void openAddPlantPage() {
        getDriver().get("http://localhost:8080/ui/plants/add");
        System.out.println("Navigated to Add Plant page: " + getDriver().getCurrentUrl());
    }

    public void navigateToPlantsPageViaSidebar() {
        plantsNavLink.waitUntilClickable().click();
        waitABit(1000);
        System.out.println("Clicked Plants sidebar link. Current URL: " + getDriver().getCurrentUrl());
    }

    // ==================== Form Input Methods ====================
    
    public void enterPlantName(String name) {
        plantNameInput.waitUntilVisible().type(name);
        System.out.println("Entered plant name: " + name);
    }

    public void selectSubCategory() {
        categoryDropdown.waitUntilVisible();
        int optionCount = categoryDropdown.getSelectOptions().size();
        if (optionCount > 1) {
            // Select the first non-empty option (index 1)
            categoryDropdown.selectByIndex(1);
            System.out.println("Selected sub-category from dropdown");
        } else {
            throw new IllegalStateException("No sub-category available in dropdown. Please ensure at least one sub-category exists and page is loaded correctly.");
        }
    }

    public void enterPrice(String price) {
        priceInput.waitUntilVisible().type(price);
        System.out.println("Entered price: " + price);
    }

    public void enterQuantity(String quantity) {
        quantityInput.waitUntilVisible().type(quantity);
        System.out.println("Entered quantity: " + quantity);
    }

    public void clickSaveButton() {
        saveButton.waitUntilEnabled().click();
        System.out.println("Clicked Save button");
    }

    // ==================== Verification Methods ====================
    
    public boolean isSuccessMessageDisplayed() {
        try {
            return successMessage.waitUntilVisible().isVisible();
        } catch (Exception e) {
            System.out.println("[DEBUG] Success message not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isOnPlantListPageAfterAdd() {
        return getDriver().getCurrentUrl().contains("/ui/plants") && !getDriver().getCurrentUrl().contains("/ui/plants/add");
    }

    public boolean isPlantInList(String plantName) {
        try {
            plantListContainer.waitUntilVisible();
            return plantListContainer.containsText(plantName);
        } catch (Exception e) {
            System.out.println("[DEBUG] Plant not found in list: " + e.getMessage());
            return false;
        }
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

    // ==================== Low Stock Badge Verification Methods ====================

    public WebElementFacade findPlantRowByQuantity(String quantity) {
        try {
            plantListContainer.waitUntilVisible();
            String xpath = "//table//tbody//tr[.//td//span[contains(@class, 'text-danger') and text()='" + quantity + "']]";
            WebElementFacade row = find(org.openqa.selenium.By.xpath(xpath));
            System.out.println("Found plant row with quantity: " + quantity);
            return row;
        } catch (Exception e) {
            System.out.println("[DEBUG] Plant row with quantity " + quantity + " not found: " + e.getMessage());
            return null;
        }
    }

    public boolean isPlantRowVisible(String quantity) {
        try {
            WebElementFacade row = findPlantRowByQuantity(quantity);
            if (row != null) {
                boolean visible = row.isVisible();
                System.out.println("Plant row with quantity " + quantity + " visible: " + visible);
                return visible;
            }
            return false;
        } catch (Exception e) {
            System.out.println("[DEBUG] Error checking plant row visibility: " + e.getMessage());
            return false;
        }
    }

    public boolean isLowBadgeDisplayedForQuantity(String quantity) {
        try {
            WebElementFacade row = findPlantRowByQuantity(quantity);
            if (row != null) {
                // Find badge within the specific row
                String badgeXpath = ".//span[@class='badge bg-danger ms-2' and contains(text(), 'Low')]";
                WebElementFacade badge = row.find(org.openqa.selenium.By.xpath(badgeXpath));
                
                boolean badgeVisible = badge.isVisible();
                String badgeText = badge.getText().trim();
                
                System.out.println("Low badge visible: " + badgeVisible);
                System.out.println("Low badge text: '" + badgeText + "'");
                
                return badgeVisible && badgeText.equals("Low");
            }
            return false;
        } catch (Exception e) {
            System.out.println("[DEBUG] Low badge not found for quantity " + quantity + ": " + e.getMessage());
            return false;
        }
    }

    public boolean verifyLowBadgeIndicatesLowStock() {
        try {
            lowStockBadge.waitUntilVisible();
            String badgeText = lowStockBadge.getText().trim();
            String badgeClass = lowStockBadge.getAttribute("class");
            
            boolean correctText = badgeText.equals("Low");
            boolean correctClass = badgeClass.contains("badge") && badgeClass.contains("bg-danger");
            
            System.out.println("Badge text: '" + badgeText + "'");
            System.out.println("Badge class: " + badgeClass);
            System.out.println("Badge indicates low stock: " + (correctText && correctClass));
            
            return correctText && correctClass;
        } catch (Exception e) {
            System.out.println("[DEBUG] Error verifying low badge: " + e.getMessage());
            return false;
        }
    }

    // ==================== Getter Methods ====================
    
    public String getAddPlantButtonText() {
        return addPlantButton.getText();
    }
    
    public String getPageHeading() {
        return pageHeading.getText();
    }
}