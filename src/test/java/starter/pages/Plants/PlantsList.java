package starter.pages.Plants;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

@DefaultUrl("http://localhost:8080/ui/plants")
public class PlantsList extends PageObject {

    // ==================== NAVIGATION METHODS ====================
    
    public void navigateToPlantsList() {
        getDriver().get("http://localhost:8080/ui/plants");
        waitABit(1500);
    }

    public void navigateToUrl(String url) {
        String fullUrl = "http://localhost:8080" + url;
        getDriver().get(fullUrl);
        waitABit(1500);
    }

    // ==================== GENERAL VERIFICATION METHODS ====================

    public boolean isPlantListPageDisplayed() {
        try {
            waitABit(1000);
            String currentUrl = getDriver().getCurrentUrl();
            boolean urlCorrect = currentUrl.contains("/ui/plants");
            boolean tablePresent = $(By.cssSelector("table")).isPresent();
            return urlCorrect && tablePresent;
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TEST CASE: UI_PlantList_PaginationNavigation(UI/UX)_006 ====================
    // Verify Normal User can navigate through pagination

    public void clickNextButton() {
        WebElementFacade nextButton = $(By.xpath("//ul[@class='pagination']//a[contains(text(), 'Next')]"));
        nextButton.waitUntilClickable();
        nextButton.click();
        waitABit(1000);
    }

    public void clickPreviousButton() {
        WebElementFacade previousButton = $(By.xpath("//ul[@class='pagination']//a[contains(text(), 'Previous')]"));
        previousButton.waitUntilClickable();
        previousButton.click();
        waitABit(1000);
    }

    public boolean isPaginationControlsVisible() {
        try {
            WebElementFacade paginationNav = $(By.cssSelector("nav ul.pagination"));
            paginationNav.waitUntilVisible();
            return paginationNav.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstPageDisplayed() {
        try {
            String currentUrl = getDriver().getCurrentUrl();
            boolean urlContainsPage0 = currentUrl.contains("page=0") || !currentUrl.contains("page=");
            
            WebElementFacade activePageNumber = $(By.cssSelector("li.page-item.active a.page-link"));
            String activePageText = activePageNumber.getText().trim();
            boolean activePageIs1 = activePageText.equals("1");

            return urlContainsPage0 && activePageIs1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlantsDisplayedOnPage(int expectedCount) {
        try {
            WebElementFacade plantTable = $(By.cssSelector("table.table"));
            plantTable.waitUntilVisible();
            
            List<WebElementFacade> plantTableRows = findAll(By.xpath("//table//tbody//tr"));
            int actualCount = plantTableRows.size();
            
            return actualCount <= expectedCount && actualCount > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSecondPageDisplayed() {
        try {
            String currentUrl = getDriver().getCurrentUrl();
            boolean urlContainsPage1 = currentUrl.contains("page=1");
            
            WebElementFacade activePageNumber = $(By.cssSelector("li.page-item.active a.page-link"));
            String activePageText = activePageNumber.getText().trim();
            boolean activePageIs2 = activePageText.equals("2");

            return urlContainsPage1 && activePageIs2;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageIndicatorUpdated(String expectedPageNumber) {
        try {
            WebElementFacade activePageNumber = $(By.cssSelector("li.page-item.active a.page-link"));
            activePageNumber.waitUntilVisible();
            String actualPageNumber = activePageNumber.getText().trim();
            
            return actualPageNumber.equals(expectedPageNumber);
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TEST CASE: UI_PlantList_SearchByName(UI/UX)_007 ====================
    // Verify Normal User can search plants by name

    public void enterSearchText(String searchText) {
        WebElementFacade searchInput = $(By.cssSelector("input[name='name']"));
        searchInput.waitUntilVisible();
        searchInput.clear();
        searchInput.type(searchText);
    }

    public void clickSearchButton() {
        WebElementFacade searchButton = $(By.cssSelector("button.btn-primary"));
        searchButton.waitUntilClickable();
        searchButton.click();
        waitABit(1000);
    }

    public boolean isSearchTextEntered(String expectedText) {
        WebElementFacade searchInput = $(By.cssSelector("input[name='name']"));
        return searchInput.getValue().equals(expectedText);
    }

    public boolean isSearchExecuted() {
        return getDriver().getCurrentUrl().contains("name=");
    }

    public boolean onlyPlantsWithNameDisplayed(String searchQuery) {
        try {
            waitABit(500);
            List<WebElementFacade> plantNames = findAll(By.cssSelector("table tbody tr td:first-child"));
            
            if (plantNames.isEmpty()) {
                return false;
            }

            for (WebElementFacade nameCell : plantNames) {
                String plantName = nameCell.getText().trim();
                if (!plantName.toLowerCase().contains(searchQuery.toLowerCase())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean searchResultsMatchQuery(String searchQuery) {
        return onlyPlantsWithNameDisplayed(searchQuery);
    }

    public String getSearchInputValue() {
        WebElementFacade searchInput = $(By.cssSelector("input[name='name']"));
        return searchInput.getValue();
    }

    // ==================== TEST CASE: UI_PlantList_FilterByCategory(UI/UX)_008 ====================
    // Verify Normal User can filter plants by category

    public void selectCategory(String categoryName) {
        WebElementFacade categoryDropdown = $(By.cssSelector("select[name='categoryId']"));
        categoryDropdown.waitUntilVisible();
        categoryDropdown.selectByVisibleText(categoryName);
    }

    public void clickSearchButtonForFilter() {
        WebElementFacade searchButton = $(By.cssSelector("button.btn-primary"));
        searchButton.waitUntilClickable();
        searchButton.click();
        waitABit(1000);
    }

    public boolean isCategorySelected(String categoryName) {
        WebElementFacade categoryDropdown = $(By.cssSelector("select[name='categoryId']"));
        String selectedText = categoryDropdown.getSelectedVisibleTextValue();
        return selectedText.equals(categoryName);
    }

    public boolean isFilterApplied() {
        return getDriver().getCurrentUrl().contains("categoryId=");
    }

    public boolean onlyPlantsFromCategoryDisplayed(String categoryName) {
        try {
            waitABit(500);
            List<WebElementFacade> categoryCells = findAll(By.cssSelector("table tbody tr td:nth-child(2)"));
            
            if (categoryCells.isEmpty()) {
                return false;
            }

            for (WebElementFacade categoryCell : categoryCells) {
                String plantCategory = categoryCell.getText().trim();
                if (!plantCategory.equals(categoryName)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean filterWorksCorrectly(String categoryName) {
        return onlyPlantsFromCategoryDisplayed(categoryName);
    }

    // ==================== TEST CASE: UI_PlantList_SortingByPrice(UI/UX)_009 ====================
    // Verify Normal User can sort plants by price

    public void clickPriceColumnHeader() {
        WebElementFacade priceHeader = $(By.xpath("//th/a[contains(text(), 'Price')]"));
        priceHeader.waitUntilClickable();
        priceHeader.click();
        waitABit(1000);
    }

    public boolean isPriceSortedAscending() {
        try {
            waitABit(500);
            String currentUrl = getDriver().getCurrentUrl();
            return currentUrl.contains("sortField=price") && currentUrl.contains("sortDir=asc");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPriceSortedDescending() {
        try {
            waitABit(500);
            String currentUrl = getDriver().getCurrentUrl();
            return currentUrl.contains("sortField=price") && currentUrl.contains("sortDir=desc");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLowestPriceFirst() {
        try {
            waitABit(500);
            List<WebElementFacade> priceCells = findAll(By.cssSelector("table tbody tr td:nth-child(3)"));
            
            if (priceCells.isEmpty()) {
                return false;
            }

            String firstPriceText = priceCells.get(0).getText().trim();
            double firstPrice = Double.parseDouble(firstPriceText);

            double minPrice = Double.MAX_VALUE;
            for (WebElementFacade priceCell : priceCells) {
                String priceText = priceCell.getText().trim();
                double price = Double.parseDouble(priceText);
                if (price < minPrice) {
                    minPrice = price;
                }
            }

            return firstPrice == minPrice;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHighestPriceFirst() {
        try {
            waitABit(500);
            List<WebElementFacade> priceCells = findAll(By.cssSelector("table tbody tr td:nth-child(3)"));
            
            if (priceCells.isEmpty()) {
                return false;
            }

            String firstPriceText = priceCells.get(0).getText().trim();
            double firstPrice = Double.parseDouble(firstPriceText);

            double maxPrice = Double.MIN_VALUE;
            for (WebElementFacade priceCell : priceCells) {
                String priceText = priceCell.getText().trim();
                double price = Double.parseDouble(priceText);
                if (price > maxPrice) {
                    maxPrice = price;
                }
            }

            return firstPrice == maxPrice;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sortingWorksCorrectly() {
        return isPriceSortedAscending() || isPriceSortedDescending();
    }

    // ==================== TEST CASE: UI_PlantAdd_AccessDenied(UI/UX)_010 ====================
    // Verify Normal User blocked from accessing Add Plant URL

    public boolean is403PageDisplayed() {
        try {
            waitABit(1000);
            String currentUrl = getDriver().getCurrentUrl();
            return currentUrl.contains("/403");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageTitleDisplayed(String expectedTitle) {
        try {
            WebElementFacade titleElement = $(By.xpath("//h2[contains(@class, 'text-danger')]"));
            String actualTitle = titleElement.getText().trim();
            return actualTitle.equals(expectedTitle);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed(String expectedMessage) {
        try {
            WebElementFacade messageElement = $(By.xpath("//p[contains(text(), 'You do not have permission')]"));
            String actualMessage = messageElement.getText().trim();
            return actualMessage.equals(expectedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGoBackLinkAvailable(String linkText) {
        try {
            WebElementFacade goBackLink = $(By.xpath("//a[contains(text(), '" + linkText + "')]"));
            return goBackLink.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccessDenied() {
        return is403PageDisplayed();
    }

    public boolean isAuthorizationEnforced() {
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/403") && !currentUrl.contains("/plants/add");
    }
}