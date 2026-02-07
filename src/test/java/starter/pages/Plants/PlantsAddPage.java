package starter.pages.Plants;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.time.Duration;

@DefaultUrl("/ui/plants")
public class PlantsAddPage extends PageObject {

    // ==================== NAVIGATION METHODS ====================
    
    public void openPage() {
        getDriver().get("http://localhost:8080/ui/plants");
        waitABit(1500);
    }

    public void openAddPlantPage() {
        getDriver().get("http://localhost:8080/ui/plants/add");
        waitABit(1500);
    }

    public void waitForDuration(int milliseconds) {
        waitABit(milliseconds);
    }

    // ==================== TEST CASE: UI_PlantList_AddButtonVisibility(UI/UX)_001 ====================
    // Verify Add Plant button visible to Admin
    
    public boolean isPlantListPageDisplayed() {
        try {
            waitABit(1000);
            boolean urlCorrect = getDriver().getCurrentUrl().contains("/ui/plants") 
                              && !getDriver().getCurrentUrl().contains("/ui/plants/add");
            
            WebElementFacade pageHeading = $(By.cssSelector("h1, h2, h3"));
            boolean headingVisible = pageHeading.isPresent() && pageHeading.isVisible();
            
            return urlCorrect && headingVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddPlantButtonVisible() {
        try {
            WebElementFacade addPlantButton = $(By.cssSelector("a[href='/ui/plants/add'].btn, a[href='/ui/plants/add']"));
            addPlantButton.waitUntilVisible();
            return addPlantButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddPlantButtonEnabled() {
        try {
            WebElementFacade addPlantButton = $(By.cssSelector("a[href='/ui/plants/add'].btn, a[href='/ui/plants/add']"));
            return addPlantButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddPlantButtonClickable() {
        try {
            WebElementFacade addPlantButton = $(By.cssSelector("a[href='/ui/plants/add'].btn, a[href='/ui/plants/add']"));
            addPlantButton.waitUntilClickable();
            return addPlantButton.isClickable();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnPlantsListPage() {
        waitABit(1500);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/ui/plants") && !currentUrl.contains("/ui/plants/add");
    }

    // ==================== TEST CASE: UI_PlantAdd_ValidCreation(UI/UX)_002 ====================
    // Verify Admin can add plant with valid data
    
    public void enterPlantName(String name) {
        try {
            WebElementFacade plantNameInput = null;
            try {
                plantNameInput = $(By.id("name"));
            } catch (Exception e) {
                try {
                    plantNameInput = $(By.name("name"));
                } catch (Exception e2) {
                    plantNameInput = $(By.cssSelector("input[placeholder*='Plant Name'], input[type='text']:first-of-type"));
                }
            }
            
            plantNameInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            plantNameInput.clear();
            plantNameInput.type(name);
            
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectSubCategory() {
        try {
            WebElementFacade categoryDropdown = null;
            try {
                categoryDropdown = $(By.id("categoryId"));
            } catch (Exception e) {
                try {
                    categoryDropdown = $(By.name("categoryId"));
                } catch (Exception e2) {
                    categoryDropdown = $(By.cssSelector("select"));
                }
            }
            
            categoryDropdown.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            waitABit(500);
            
            int optionCount = categoryDropdown.getSelectOptions().size();
            
            if (optionCount > 1) {
                categoryDropdown.selectByIndex(1);
            } else {
                throw new IllegalStateException("No sub-category available in dropdown");
            }
            
        } catch (Exception e) {
            throw e;
        }
    }

    public void enterPrice(String price) {
        try {
            WebElementFacade priceInput = null;
            try {
                priceInput = $(By.id("price"));
            } catch (Exception e) {
                try {
                    priceInput = $(By.name("price"));
                } catch (Exception e2) {
                    priceInput = $(By.cssSelector("input[type='number'], input[placeholder*='Price']"));
                }
            }
            
            priceInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            priceInput.clear();
            priceInput.type(price);
            
        } catch (Exception e) {
            throw e;
        }
    }

    public void enterQuantity(String quantity) {
        try {
            WebElementFacade quantityInput = null;
            try {
                quantityInput = $(By.id("quantity"));
            } catch (Exception e) {
                try {
                    quantityInput = $(By.name("quantity"));
                } catch (Exception e2) {
                    quantityInput = $(By.cssSelector("input[placeholder*='Quantity']"));
                }
            }
            
            quantityInput.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(5));
            quantityInput.clear();
            quantityInput.type(quantity);
            
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickSaveButton() {
        try {
            WebElementFacade saveButton = $(By.cssSelector("button.btn.btn-primary, button[type='submit']"));
            saveButton.waitUntilEnabled().withTimeoutOf(Duration.ofSeconds(5));
            saveButton.click();
            waitABit(3000);
            
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isOnPlantListPageAfterAdd() {
        try {
            int maxAttempts = 20;
            int attempts = 0;
            
            while (attempts < maxAttempts) {
                String currentUrl = getDriver().getCurrentUrl();
                
                if (currentUrl.contains("/ui/plants") && !currentUrl.contains("/ui/plants/add")) {
                    try {
                        WebElementFacade pageContent = $(By.cssSelector("table, .plant-list, .container, body"));
                        pageContent.waitUntilPresent().withTimeoutOf(Duration.ofSeconds(3));
                    } catch (Exception e) {
                        // Continue
                    }
                    return true;
                }
                
                waitABit(500);
                attempts++;
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            String currentUrl = getDriver().getCurrentUrl();
            boolean onListPage = currentUrl.contains("/ui/plants") && !currentUrl.contains("/ui/plants/add");
            
            if (!onListPage) {
                return false;
            }
            
            waitABit(300);
            
            String[] successSelectors = {
                ".alert-success",
                ".alert.alert-success",
                "div[class*='alert'][class*='success']",
                ".alert.success",
                "[role='alert'].alert-success",
                ".alert-dismissible.alert-success"
            };
            
            for (String selector : successSelectors) {
                try {
                    WebElementFacade successMessage = $(By.cssSelector(selector));
                    successMessage.waitUntilPresent().withTimeoutOf(Duration.ofSeconds(3));
                    
                    if (successMessage.isPresent() && successMessage.isVisible()) {
                        String messageText = successMessage.getText();
                        
                        if (messageText.toLowerCase().contains("success") || 
                            messageText.toLowerCase().contains("added")) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlantInList(String plantName) {
        try {
            waitABit(2000);
            
            WebElementFacade plantTable = $(By.cssSelector("table, .plant-list, .table"));
            plantTable.waitUntilPresent().withTimeoutOf(Duration.ofSeconds(10));
            
            if (plantTable.isPresent()) {
                String tableText = plantTable.getText();
                return tableText.contains(plantName);
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TEST CASE: UI_PlantAdd_NameLengthValidation(UI/UX)_003 ====================
    // Verify plant name length validation (3-25 chars)
    
    public boolean isNameValidationErrorDisplayed(String expectedMessage) {
        try {
            WebElementFacade nameValidationError = $(By.cssSelector("#name ~ .text-danger, input[name='name'] ~ .text-danger"));
            nameValidationError.waitUntilVisible();
            
            boolean visible = nameValidationError.isVisible();
            String text = nameValidationError.getText();
            boolean correctText = text.contains(expectedMessage);
            
            return visible && correctText;
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TEST CASE: UI_PlantAdd_PriceValidation(UI/UX)_004 ====================
    // Verify price validation (must be > 0)
    
    public boolean isPriceValidationErrorDisplayed(String expectedMessage) {
        try {
            WebElementFacade priceValidationError = $(By.cssSelector("#price ~ .text-danger, input[name='price'] ~ .text-danger"));
            priceValidationError.waitUntilVisible();
            
            boolean visible = priceValidationError.isVisible();
            String text = priceValidationError.getText();
            boolean correctText = text.contains(expectedMessage);
            
            return visible && correctText;
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TEST CASE: UI_PlantList_LowBadgeDisplay(UI/UX)_005 ====================
    // Verify Low badge displays when quantity < 5
    
    public WebElementFacade findPlantRowByQuantity(String quantity) {
        try {
            waitABit(1000);
            String xpath = "//table//tbody//tr[.//td[contains(text(), '" + quantity + "')]]";
            WebElementFacade row = $(By.xpath(xpath));
            return row;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isPlantRowVisible(String quantity) {
        try {
            WebElementFacade row = findPlantRowByQuantity(quantity);
            if (row != null) {
                return row.isVisible();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLowBadgeDisplayedForQuantity(String quantity) {
        try {
            waitABit(1000);
            WebElementFacade lowBadge = $(By.xpath("//span[contains(@class, 'badge') and contains(text(), 'Low')]"));
            return lowBadge.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyLowBadgeIndicatesLowStock() {
        try {
            WebElementFacade lowStockBadge = $(By.xpath("//span[contains(@class, 'badge') and contains(text(), 'Low')]"));
            lowStockBadge.waitUntilVisible();
            String badgeText = lowStockBadge.getText().trim();
            String badgeClass = lowStockBadge.getAttribute("class");
            
            boolean correctText = badgeText.equals("Low");
            boolean correctClass = badgeClass.contains("badge");
            
            return correctText && correctClass;
        } catch (Exception e) {
            return false;
        }
    }
}