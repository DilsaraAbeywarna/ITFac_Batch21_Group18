package starter.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class PlantsEditDelete extends PageObject {

    // Dashboard Manage Plants button
    @FindBy(css = "a[href='/ui/plants']")
    private WebElementFacade managePlantsButton;

    // Edit button in Plants list
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

    // Category dropdown field
    @FindBy(id = "categoryId")
    private WebElementFacade categoryDropdown;

    // Save button
    @FindBy(css = "button.btn-primary")
    private WebElementFacade saveButton;

    // Cancel button
    @FindBy(css = "a.btn.btn-secondary")
    private WebElementFacade cancelButton;

    // Plant name error message
    @FindBy(css = "div.text-danger")
    private WebElementFacade errorMessage;

    // Category error message
    @FindBy(xpath = "//select[@id='categoryId']/following-sibling::div[@class='text-danger']")
    private WebElementFacade categoryErrorMessage;

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
        try {
            WebElementFacade subCategoryDropdown = find(By.cssSelector("select[name='subcategoryId']"));
            if (subCategoryDropdown.isPresent()) {
                subCategoryDropdown.selectByValue(subCategoryValue);
            }
        } catch (Exception e) {
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

    public void selectCategoryByValue(String categoryValue) {
        WebElementFacade categorySelect = find(By.id("categoryId"));
        if (categoryValue.equals("0") || categoryValue.isEmpty()) {
            categorySelect.selectByValue("");
        } else {
            categorySelect.selectByValue(categoryValue);
        }
    }

    public void selectDefaultCategory() {
        WebElementFacade categorySelect = find(By.id("categoryId"));
        categorySelect.selectByValue("");
    }

    public boolean isCategoryErrorMessageDisplayed() {
        try {
            return categoryErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCategoryErrorMessage() {
        return categoryErrorMessage.getText();
    }

    public boolean isOnPlantListPage() {
        waitFor(3).seconds();
        return getDriver().getCurrentUrl().contains("/ui/plants") && 
               !getDriver().getCurrentUrl().contains("/ui/plants/edit");
    }

    public void selectCategoryByVisibleText(String categoryText) {
        categoryDropdown.selectByVisibleText(categoryText);
    }

    public void clearAndEnterPlantName(String plantName) {
        plantNameField.waitUntilVisible();
        plantNameField.clear();
        plantNameField.sendKeys(plantName);
    }

    public void clearAndEnterPrice(String price) {
        priceField.waitUntilVisible();
        priceField.clear();
        priceField.sendKeys(price);
    }

    public void clearAndEnterQuantity(String quantity) {
        quantityField.waitUntilVisible();
        quantityField.clear();
        quantityField.sendKeys(quantity);
    }

    public void clickCancelButton() {
        cancelButton.waitUntilClickable().click();
    }

    public String getPlantNameValue() {
        return plantNameField.getValue();
    }

    // Low badge element
    @FindBy(css = "span.badge.bg-danger.ms-2")
    private WebElementFacade lowBadge;

    public boolean isLowBadgeDisplayed() {
        try {
            waitFor(2).seconds();
            return lowBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // UI_PLANTEDIT_EDITBUTTON_12 - Check if Edit button is visible
    public boolean isEditButtonVisible() {
        try {
            waitFor(2).seconds();
            return firstEditButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    // Delete button element
    @FindBy(css = "a[title='Delete']")
    private WebElementFacade firstDeleteButton;

    // UI_PLANTDELETE_DELETEBUTTON_13 - Check if Delete button is visible
    public boolean isDeleteButtonVisible() {
        try {
            waitFor(2).seconds();
            return firstDeleteButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    // UI_PLANTEDIT_CATEGORY_08 - Get current selected category
    public String getCurrentSelectedCategory() {
        return categoryDropdown.getSelectedVisibleTextValue();
    }

    // UI_PLANTEDIT_CATEGORY_08 - Get category from plant list
    @FindBy(xpath = "//table//tbody/tr[1]/td[4]")
    private WebElementFacade firstPlantCategory;

    public String getFirstPlantCategoryInList() {
        try {
            waitFor(2).seconds();
            return firstPlantCategory.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}

