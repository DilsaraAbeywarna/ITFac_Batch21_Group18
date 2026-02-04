package starter.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class PlantsEditDelete extends PageObject {

    // Dashboard → Manage Plants button
    @FindBy(css = "a[href='/ui/plants']")
    private WebElementFacade managePlantsButton;

    // First Edit button in Plants list
    @FindBy(css = "a[title='Edit']")
    private WebElementFacade firstEditButton;

    // Plant Name input field
    @FindBy(id = "name")
    private WebElementFacade plantNameField;

    // Price input field
    @FindBy(css = "input[name='price']")
    private WebElementFacade priceField;

    // Quantity input field
    @FindBy(css = "input[name='quantity']")
    private WebElementFacade quantityField;

    // Save button
    @FindBy(css = "button.btn-primary")
    private WebElementFacade saveButton;

    // Plant name error message
    @FindBy(css = "div.text-danger")
    private WebElementFacade errorMessage;

    // Price error message
    @FindBy(xpath = "//label[text()='Price']/following-sibling::input/following-sibling::div[@class='text-danger']")
    private WebElementFacade priceErrorMessage;

    //quantity error message
    @FindBy(xpath = "//input[@id='quantity']/following-sibling::div[@class='text-danger']")
    private WebElementFacade quantityErrorMessage;

    

    public void navigateToPlantsPage() {
        managePlantsButton.waitUntilClickable().click();
    }

    public void clickEditButton() {
        firstEditButton.waitUntilClickable().click();
    }

    public boolean isOnEditPlantPage() {
        return getDriver().getCurrentUrl().contains("/ui/plants/edit");
    }

    public void clearPlantNameField() {
        plantNameField.clear();
    }

    public void selectSubCategory(String subCategoryValue) {
        // Try to find and select subcategory dropdown
        try {
            WebElementFacade subCategoryDropdown = find(By.cssSelector("select[name='subcategoryId']"));
            if (subCategoryDropdown.isPresent()) {
                subCategoryDropdown.selectByValue(subCategoryValue);
            }
        } catch (Exception e) {
            // Skip if subcategory dropdown is not found
        }
    }

    public void enterPrice(String price) {
        priceField.type(price);
    }

    public void enterQuantity(String quantity) {
        quantityField.type(quantity);
    }

    public void clickSaveButton() {
        saveButton.waitUntilClickable().click();
    }

    public boolean isPlantNameErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPlantNameErrorMessage() {
        return errorMessage.getText();
    }

    public void clearPriceField() {
        priceField.clear();
    }

    public void enterPlantName(String plantName) {
        plantNameField.type(plantName);
    }

    public boolean isPriceErrorMessageDisplayed() {
        try {
            return priceErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPriceErrorMessage() {
        return priceErrorMessage.getText();
    }

     public void clearquantityField() {
        quantityField.clear();
    }

        public boolean isquantityErrorMessageDisplayed() {
        try {
            return quantityErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getquantityErrorMessage() {
        return quantityErrorMessage.getText();
    }
}
