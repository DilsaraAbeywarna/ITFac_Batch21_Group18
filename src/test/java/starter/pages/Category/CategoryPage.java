package starter.pages.Category;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("/ui/categories")
public class CategoryPage extends PageObject {

    @FindBy(css = ".header h2")
    private WebElementFacade pageTitle;

    @FindBy(css = "a[href='/ui/categories/add']")
    private WebElementFacade addCategoryButton;

    @FindBy(css = "h3.mb-4")
    private WebElementFacade subHeader;

    @FindBy(css = "input[name='name'][placeholder='Search sub category']")
    private WebElementFacade searchInput;

    @FindBy(css = "select[name='parentId']")
    private WebElementFacade parentSelect;

    @FindBy(css = "button[type='submit'].btn.btn-primary")
    private WebElementFacade searchButton;

    @FindBy(css = "a.btn.btn-outline-secondary[href='/ui/categories']")
    private WebElementFacade resetButton;

    @FindBy(css = "table.table")
    private WebElementFacade categoryTable;

    // add category page attributes
    @FindBy(css = "h3")
    private WebElementFacade addCategoryPageTitle;

    @FindBy(css = "input#name")
    private WebElementFacade categoryNameInput;

    @FindBy(css = "select#parentId")
    private WebElementFacade parentCategorySelect;

    @FindBy(css = "button[type='submit'].btn.btn-primary")
    private WebElementFacade saveButton;

    @FindBy(css = "a.btn.btn-secondary[href='/ui/categories']")
    private WebElementFacade cancelButton;

    @FindBy(css = ".form-text")
    private WebElementFacade parentCategoryHelpText;

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/categories");
        System.out.println("Explicitly navigated to: " + getDriver().getCurrentUrl());
    }

    /**
     * Verify we're on the category page (defensive version)
     */
    /**
     * Verify we're on the category page (fixed version)
     */
    public void verifyOnCategoryPage() {
        waitABit(1000);

        try {
            // The page title is actually in <h2>, not <h1>
            WebElementFacade title = find(By.cssSelector(".header h2"));
            title.waitUntilVisible();

            String titleText = title.getText();
            if (!titleText.contains("QA Training Application")) {
                throw new AssertionError(
                        "Expected page title to contain 'QA Training Application' but got: " + titleText);
            }
            System.out.println("✓ Page title verified: " + titleText);
        } catch (Exception e) {
            System.err.println("❌ Error verifying page title: " + e.getMessage());
            throw e;
        }

        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL in verifyOnCategoryPage: " + currentUrl);

        // Make sure we're on the LIST page, not the ADD page
        if (!currentUrl.equals("http://localhost:8080/ui/categories") &&
                !currentUrl.matches("http://localhost:8080/ui/categories\\?.*")) {
            throw new AssertionError("Expected to be on category list page but URL is: " + currentUrl);
        }

        // Verify the sub header "Categories"
        try {
            WebElementFacade header = find(By.cssSelector("h3.mb-4"));
            header.waitUntilVisible();
            String headerText = header.getText();
            if (!headerText.contains("Categories")) {
                throw new AssertionError("Expected subheader to contain 'Categories' but got: " + headerText);
            }
            System.out.println("✓ Subheader verified: " + headerText);
        } catch (Exception e) {
            System.err.println("❌ Error verifying subheader: " + e.getMessage());
            throw e;
        }

        System.out.println("✓ Successfully verified on category page");
    }

    public void isPageTitleVisibleWithText(String text) {
        if (pageTitle == null) {
            pageTitle = find(By.cssSelector(".header h2"));
        }
        pageTitle.shouldBeVisible();
        pageTitle.shouldContainText(text);
    }

    public void isSubHeaderVisibleWithText(String text) {
        if (subHeader == null) {
            subHeader = find(By.cssSelector("h3.mb-4"));
        }
        subHeader.shouldBeVisible();
        subHeader.shouldContainText(text);
    }

    public void isAddACategoryButtonVisible() {
        addCategoryButton.waitUntilVisible();
        addCategoryButton.shouldBeVisible();
        addCategoryButton.shouldContainText("Add A Category");
        System.out.println("✓ Add A Category button is visible");
    }

    public void isInputFeildVisibleWithLabel() {
        WebElementFacade input = find(By.cssSelector("input[name='name'][placeholder='Search sub category']"));
        input.waitUntilVisible();
        input.shouldBeVisible();

        String placeholder = input.getWrappedElement().getAttribute("placeholder");
        if (placeholder == null || !placeholder.equals("Search sub category")) {
            throw new AssertionError("Expected placeholder 'Search sub category' but got: " + placeholder);
        }

        System.out.println("✓ Search input with placeholder 'Search sub category' is visible");
    }

    public void isParentSelectVisible() {
        WebElementFacade select = find(By.cssSelector("select[name='parentId']"));
        select.waitUntilVisible();
        select.shouldBeVisible();

        // Check if "All Parents" option exists
        WebElementFacade allParentsOption = find(By.cssSelector("select[name='parentId'] option[value='']"));
        allParentsOption.shouldContainText("All Parents");

        // Verify it's selected by default (value should be empty)
        String selectedValue = select.getSelectedValue();
        if (selectedValue == null || !selectedValue.isEmpty()) {
            throw new AssertionError("Expected 'All Parents' to be selected but got value: " + selectedValue);
        }

        System.out.println("✓ Parent select with 'All Parents' selected is visible");

    }

    public void isSearchButtonVisible() {
        WebElementFacade button = find(By.cssSelector("button[type='submit'].btn.btn-primary"));
        button.waitUntilVisible();
        button.shouldBeVisible();
        button.shouldContainText("Search");
        System.out.println("✓ Search button is visible");
    }

    public void isResetButtonVisible() {
        WebElementFacade button = find(By.cssSelector("a.btn.btn-outline-secondary[href='/ui/categories']"));
        button.waitUntilVisible();
        button.shouldBeVisible();
        button.shouldContainText("Reset");
        System.out.println("✓ Reset button is visible");
    }

    public void isCategoryTableVisible() {
        WebElementFacade table = find(By.cssSelector("table.table"));
        table.waitUntilVisible();
        table.shouldBeVisible();
        System.out.println("✓ Category table is visible");
    }

    public void clickAddCategoryButton() {
        WebElementFacade addButton = find(By.cssSelector("a[href='/ui/categories/add']"));
        addButton.waitUntilVisible();
        addButton.waitUntilClickable();
        addButton.click();
        System.out.println("✓ Clicked Add Category button");
        waitABit(1000);
    }

    public void userTypeInSearchInput(String categoryName) {
        WebElementFacade searchField = find(By.cssSelector("input[name='name']"));
        searchField.waitUntilVisible();
        searchField.clear();
        searchField.type(categoryName);
        System.out.println("✓ User typed in search input: " + categoryName);
    }

    public void clickSearchButton() {
        WebElementFacade searchBtn = find(By.cssSelector("button[type='submit'].btn.btn-primary"));
        searchBtn.waitUntilVisible();
        searchBtn.waitUntilClickable();
        searchBtn.click();
        System.out.println("✓ Clicked Search button");
        waitABit(2000);
    }

    public void typeInSearchInput(String searchText) {
        WebElementFacade searchField = find(By.cssSelector("input[name='name']"));
        searchField.waitUntilVisible();
        searchField.clear();
        searchField.type(searchText);
        System.out.println("✓ Typed '" + searchText + "' in search input");
    }

    public void verifyOnlyCategoryAppears(String categoryName) {
        waitABit(1000); // Wait for search results to load

        // Find all rows in the table body
        List<WebElementFacade> tableRows = findAll("table.table tbody tr");

        System.out.println("🔍 Verifying only '" + categoryName + "' appears in results...");
        System.out.println("Found " + tableRows.size() + " row(s) in table");

        if (tableRows.isEmpty()) {
            throw new AssertionError("❌ No results found in table after search");
        }

        // Check if the empty state row is showing
        WebElementFacade emptyStateRow = find(By.cssSelector("table.table tbody tr td[colspan]"));
        if (emptyStateRow.isPresent() && emptyStateRow.isVisible()) {
            String emptyMessage = emptyStateRow.getText();
            throw new AssertionError("❌ Search returned no results. Message: " + emptyMessage);
        }

        // Verify each row contains the search term
        boolean allRowsMatch = true;
        List<String> foundCategories = new ArrayList<>();

        for (WebElementFacade row : tableRows) {
            // Get the category name from the second column (Name column)
            List<WebElementFacade> cells = row.thenFindAll("td");
            if (cells.size() >= 2) {
                String rowCategoryName = cells.get(1).getText().trim();
                foundCategories.add(rowCategoryName);

                // Check if the row contains the search term (case-insensitive)
                if (!rowCategoryName.toLowerCase().contains(categoryName.toLowerCase())) {
                    allRowsMatch = false;
                    System.err.println("❌ Row contains unexpected category: " + rowCategoryName);
                } else {
                    System.out.println("✓ Row contains matching category: " + rowCategoryName);
                }
            }
        }

        if (!allRowsMatch) {
            throw new AssertionError(
                    String.format("❌ Search results contain categories that don't match '%s'. Found: %s",
                            categoryName,
                            String.join(", ", foundCategories)));
        }

        System.out.println("✓ All " + tableRows.size() + " row(s) contain '" + categoryName + "'");
    }

    public void verifyCategoryInSearchResults(String categoryName) {
        waitABit(500);

        // Find the category name in the table
        WebElementFacade categoryCell = find(
                By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(), '" + categoryName
                        + "')]"));

        if (!categoryCell.isPresent() || !categoryCell.isVisible()) {
            throw new AssertionError("❌ Category '" + categoryName + "' not found in search results");
        }

        System.out.println("✓ Category '" + categoryName + "' found in search results");
    }

    public int getTableRowCount() {
        List<WebElementFacade> tableRows = findAll("table.table tbody tr");

        // Filter out empty state rows
        long actualRows = tableRows.stream()
                .filter(row -> !row.findElements(By.cssSelector("td[colspan]")).isEmpty() == false)
                .count();

        System.out.println("Table contains " + actualRows + " row(s)");
        return (int) actualRows;
    }

    public void clearSearchInput() {
        WebElementFacade searchField = find(By.cssSelector("input[name='name']"));
        searchField.waitUntilVisible();
        searchField.clear();
        System.out.println("✓ Cleared search input");
    }

    public void clickResetButton() {
        WebElementFacade resetBtn = find(By.cssSelector("a.btn.btn-outline-secondary[href='/ui/categories']"));
        resetBtn.waitUntilVisible();
        resetBtn.waitUntilClickable();
        resetBtn.click();
        System.out.println("✓ Clicked Reset button");
        waitABit(1000);
    }

    public void verifyNoCategoryFoundMessage(String expectedMessage) {
        System.out.println("🔍 Looking for message: '" + expectedMessage + "'");
        waitABit(2000);

        // DEBUG: Print what's on the page
        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("📍 Current URL: " + currentUrl);

        // DEBUG: Check if table exists
        try {
            WebElementFacade table = find(By.cssSelector("table"));
            System.out.println("✅ Table found, classes: " + table.getDomAttribute("class"));
        } catch (Exception e) {
            System.err.println("❌ No table found!");
        }

        // FIX: Use simpler selector that matches your actual HTML
        // Option 1: Just look for ANY td with colspan="4"
        WebElementFacade emptyStateCell = find(By.cssSelector("td[colspan='4']"));

        // Wait longer with explicit timeout
        emptyStateCell.waitUntilVisible().withTimeoutOf(Duration.ofSeconds(10));

        String actualMessage = emptyStateCell.getText().trim();
        System.out.println("✅ Found message: '" + actualMessage + "'");

        if (!actualMessage.equalsIgnoreCase(expectedMessage)) {
            throw new AssertionError(
                    String.format("Expected '%s' but got '%s'", expectedMessage, actualMessage));
        }

        System.out.println("✓ Verified: " + expectedMessage);
    }

    public void verifyRecordCount(int expectedCount) {
        int actualCount = getDisplayedRecordCount();

        if (actualCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d records but found %d records", expectedCount, actualCount));
        }

        System.out.println("✓ Verified " + expectedCount + " records are displayed");
    }

    public void isPaginationVisible() {
        WebElementFacade pagination = find(By.cssSelector("nav[aria-label='Page navigation'], .pagination"));
        pagination.waitUntilVisible();
        pagination.shouldBeVisible();
        System.out.println("✓ Pagination navigation is visible");
    }

    public boolean isNextButtonVisible() {
        try {
            waitABit(500);
            // Find all pagination links and check text manually
            List<WebElementFacade> allLinks = findAll("ul.pagination li a.page-link");

            for (WebElementFacade link : allLinks) {
                String linkText = link.getText().trim();
                if (linkText.equals("Next")) {
                    System.out.println("✓ Next button visible: " + link.isVisible());
                    return link.isVisible();
                }
            }

            System.err.println("❌ Next button not found in pagination links");
            return false;
        } catch (Exception e) {
            System.err.println("❌ Error finding Next button: " + e.getMessage());
            return false;
        }
    }

    public boolean isPreviousButtonVisible() {
        try {
            waitABit(500);
            // Find all pagination links and check text manually
            List<WebElementFacade> allLinks = findAll("ul.pagination li a.page-link");

            for (WebElementFacade link : allLinks) {
                String linkText = link.getText().trim();
                if (linkText.equals("Previous")) {
                    System.out.println("✓ Previous button visible: " + link.isVisible());
                    return link.isVisible();
                }
            }

            System.err.println("❌ Previous button not found in pagination links");
            return false;
        } catch (Exception e) {
            System.err.println("❌ Error finding Previous button: " + e.getMessage());
            return false;
        }
    }

    public void verifyNextAndPreviousButtonsVisible() {
        waitABit(1000); // Give pagination more time to render

        // Debug: Print the entire pagination HTML
        try {
            WebElementFacade paginationContainer = find(By.cssSelector("ul.pagination"));
            System.out.println(
                    "🔍 Pagination HTML: " + paginationContainer.getWrappedElement().getAttribute("outerHTML"));
        } catch (Exception e) {
            System.err.println("❌ Could not find pagination container");
        }

        // Debug: Print all pagination links found
        List<WebElementFacade> allLinks = findAll("ul.pagination li a.page-link");
        System.out.println("🔍 Debug: Found " + allLinks.size() + " pagination links:");
        for (WebElementFacade link : allLinks) {
            System.out.println("  - Text: '" + link.getText() + "', Visible: " + link.isVisible() + ", Href: "
                    + link.getDomAttribute("href"));
        }

        boolean nextVisible = isNextButtonVisible();
        boolean prevVisible = isPreviousButtonVisible();

        if (!nextVisible) {
            throw new AssertionError("❌ Next button is not visible");
        }
        if (!prevVisible) {
            throw new AssertionError("❌ Previous button is not visible");
        }

        System.out.println("✓ Next and Previous buttons are visible");
    }

    public List<WebElementFacade> getPageNumberButtons() {
        // Find all page number links (excluding Previous/Next)
        List<WebElementFacade> pageButtons = findAll("ul.pagination li.page-item a.page-link");

        // Filter to get only numeric page buttons
        List<WebElementFacade> numericButtons = pageButtons.stream()
                .filter(button -> {
                    String text = button.getText().trim();
                    return text.matches("\\d+"); // Only keep buttons with numbers
                })
                .collect(java.util.stream.Collectors.toList());

        System.out.println("📄 Found " + numericButtons.size() + " page number buttons");
        return numericButtons;
    }

    public void verifyPageButtonCount(int expectedCount) {
        List<WebElementFacade> pageButtons = getPageNumberButtons();
        int actualCount = pageButtons.size();

        if (actualCount != expectedCount) {
            throw new AssertionError(
                    String.format("Expected %d page buttons but found %d", expectedCount, actualCount));
        }

        System.out.println("✓ Verified " + expectedCount + " page buttons are displayed");
    }

    public void clickNextButton() {
        waitABit(500);

        // Find Next button by iterating through pagination links
        List<WebElementFacade> allLinks = findAll("ul.pagination li a.page-link");
        WebElementFacade nextButton = null;

        for (WebElementFacade link : allLinks) {
            if (link.getText().trim().equals("Next")) {
                nextButton = link;
                break;
            }
        }

        if (nextButton == null) {
            throw new AssertionError("❌ Next button not found");
        }

        // Check if parent li has disabled class
        WebElementFacade parentLi = nextButton.findBy(By.xpath(".."));
        String classes = parentLi.getDomAttribute("class");

        if (classes != null && classes.contains("disabled")) {
            throw new AssertionError("❌ Cannot click Next button - it is disabled");
        }

        // Use Serenity's withAction() to move and click
        nextButton.waitUntilVisible();

        try {
            // Try regular click first
            nextButton.click();
        } catch (Exception e) {
            // If regular click fails, use Actions
            System.out.println("⚠️ Regular click failed, using Actions moveToElement...");
            withAction().moveToElement(nextButton).click().perform();
        }

        System.out.println("✓ Clicked Next button");
        waitABit(1500); // Wait for page to load
    }

    public void clickPreviousButton() {
        waitABit(500);

        // Find Previous button by iterating through pagination links
        List<WebElementFacade> allLinks = findAll("ul.pagination li a.page-link");
        WebElementFacade prevButton = null;

        for (WebElementFacade link : allLinks) {
            if (link.getText().trim().equals("Previous")) {
                prevButton = link;
                break;
            }
        }

        if (prevButton == null) {
            throw new AssertionError("❌ Previous button not found");
        }

        // Check if parent li has disabled class
        WebElementFacade parentLi = prevButton.findBy(By.xpath(".."));
        String classes = parentLi.getDomAttribute("class");

        if (classes != null && classes.contains("disabled")) {
            throw new AssertionError("❌ Cannot click Previous button - it is disabled");
        }

        // Use Serenity's withAction() to move and click
        prevButton.waitUntilVisible();

        try {
            // Try regular click first
            prevButton.click();
        } catch (Exception e) {
            // If regular click fails, use Actions
            System.out.println("⚠️ Regular click failed, using Actions moveToElement...");
            withAction().moveToElement(prevButton).click().perform();
        }

        System.out.println("✓ Clicked Previous button");
        waitABit(1500); // Wait for page to load
    }

    public void clickColumnHeader(String columnName) {
        waitABit(500);
        
        // Find the column header link by text
        WebElementFacade columnHeader = find(
            By.xpath("//table[contains(@class, 'table')]//thead//th//a[contains(text(), '" + columnName + "')]")
        );
        
        columnHeader.waitUntilVisible();
        columnHeader.waitUntilClickable();
        
        System.out.println("🖱️ Clicking column header: " + columnName);
        columnHeader.click();
        
        waitABit(2000); // Wait for sort to apply and page to reload
        System.out.println("✓ Clicked column header: " + columnName);
    }

    /**
     * Get the sort direction icon for a column
     */
    public String getSortDirection(String columnName) {
        waitABit(500);
        
        try {
            // Find the column header
            WebElementFacade columnHeader = find(
                By.xpath("//table[contains(@class, 'table')]//thead//th//a[contains(text(), '" + columnName + "')]")
            );
            
            // Look for the sort icon span inside the link
            List<WebElementFacade> spans = columnHeader.thenFindAll("span");
            
            if (spans.isEmpty()) {
                System.out.println("📊 No sort icon found for column: " + columnName);
                return "none";
            }
            
            String iconText = spans.get(0).getText().trim();
            System.out.println("🔍 Sort icon text for " + columnName + ": '" + iconText + "'");
            
            // ↓ = descending, ↑ = ascending
            if (iconText.contains("↓") || iconText.contains("▼")) {
                return "desc";
            } else if (iconText.contains("↑") || iconText.contains("▲")) {
                return "asc";
            }
            
            return "none";
            
        } catch (Exception e) {
            System.err.println("❌ Error getting sort direction: " + e.getMessage());
            return "none";
        }
    }
    /**
     * Get all category IDs from the current page
     */
    public List<Integer> getCategoryIds() {
        waitABit(500);
        
        List<WebElementFacade> idCells = findAll("table.table tbody tr td:first-child");
        List<Integer> ids = new ArrayList<>();
        
        for (WebElementFacade cell : idCells) {
            try {
                String idText = cell.getText().trim();
                if (!idText.isEmpty() && idText.matches("\\d+")) {
                    ids.add(Integer.parseInt(idText));
                }
            } catch (NumberFormatException e) {
                // Skip non-numeric cells
            }
        }
        
        System.out.println("📊 Category IDs found: " + ids);
        return ids;
    }

    public void verifySortedByIdAscending() {
        List<Integer> ids = getCategoryIds();
        
        if (ids.isEmpty()) {
            throw new AssertionError("No category IDs found");
        }
        
        // Check if sorted in ascending order
        for (int i = 0; i < ids.size() - 1; i++) {
            if (ids.get(i) > ids.get(i + 1)) {
                throw new AssertionError(
                    String.format("Categories not sorted by ID in ascending order: %d > %d at position %d",
                        ids.get(i), ids.get(i + 1), i)
                );
            }
        }
        
        System.out.println("✓ Categories are sorted by ID in ascending order: " + ids);
    }

    public void verifySortedByIdDescending() {
        List<Integer> ids = getCategoryIds();
        
        if (ids.isEmpty()) {
            throw new AssertionError("No category IDs found");
        }
        
        // Check if sorted in descending order
        for (int i = 0; i < ids.size() - 1; i++) {
            if (ids.get(i) < ids.get(i + 1)) {
                throw new AssertionError(
                    String.format("Categories not sorted by ID in descending order: %d < %d at position %d",
                        ids.get(i), ids.get(i + 1), i)
                );
            }
        }
        
        System.out.println("✓ Categories are sorted by ID in descending order: " + ids);
    }

    public void verifySortIcon(String columnName, String expectedDirection) {
        String actualDirection = getSortDirection(columnName);
        System.out.println("🔍 Verifying sort icon for column '" + columnName + "': expected '" + expectedDirection + "', actual '" + actualDirection + "'");
        
        if (!actualDirection.equals(expectedDirection)) {
            throw new AssertionError(
                String.format("Expected sort direction '%s' for column '%s' but got '%s'", 
                    expectedDirection, columnName, actualDirection)
            );
        }
        
        System.out.println("✓ Sort icon verified: " + columnName + " - " + expectedDirection);
    }

    // Add category page

    public void adminNavigatesToAddCategoryPage() {
        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL in adminNavigatesToAddCategoryPage: " + currentUrl);

        if (!currentUrl.contains("/ui/categories/add")) {
            throw new AssertionError("Expected to be on add category page but URL is: " + currentUrl);
        }

        System.out.println("✓ Successfully navigated to add category page");
    }

    public void verifyOnAddCategoryPage() {
        waitABit(1000);

        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL in verifyOnAddCategoryPage: " + currentUrl);

        if (!currentUrl.contains("/ui/categories/add")) {
            throw new AssertionError("Expected to be on add category page but URL is: " + currentUrl);
        }

        addCategoryPageTitle.waitUntilVisible();
        addCategoryPageTitle.shouldContainText("Add Category");

        System.out.println("✓ Successfully verified on add category page");
    }

    public void isCategoryNameInputVisible() {
        categoryNameInput.waitUntilVisible();
        categoryNameInput.shouldBeVisible();
        System.out.println("✓ Category name input field is visible");
    }

    public void isAddCategoryPageTitleVisible() {
        addCategoryPageTitle.waitUntilVisible();
        addCategoryPageTitle.shouldBeVisible();
        addCategoryPageTitle.shouldContainText("Add Category");
        System.out.println("✓ Add Category page title is visible");
    }

    public void isParentCategorySelectVisible() {
        parentCategorySelect.waitUntilVisible();
        parentCategorySelect.shouldBeVisible();
        System.out.println("✓ Parent category select is visible");
    }

    public void isParentCategoryHelpTextVisible() {
        parentCategoryHelpText.waitUntilVisible();
        parentCategoryHelpText.shouldContainText("Leave empty to create a main category");
        System.out.println("✓ Parent category help text is visible");
    }

    public void enterCategoryName(String categoryName) {
        WebElementFacade nameField = find(By.cssSelector("input#name"));
        nameField.waitUntilVisible();
        nameField.clear();
        nameField.type(categoryName);
        System.out.println("✓ Entered category name: " + categoryName);
    }

    public void selectParentCategory(String parentName) {
        WebElementFacade parentSelect = find(By.cssSelector("select#parentId"));
        parentSelect.waitUntilVisible();

        if (parentName == null || parentName.isEmpty() || parentName.equals("Main Category")) {
            parentSelect.selectByValue("");
            System.out.println("✓ Selected Main Category (no parent)");
        } else {
            parentSelect.selectByVisibleText(parentName);
            System.out.println("✓ Selected parent category: " + parentName);
        }
    }

    public void clickSaveButton() {
        WebElementFacade saveBtn = find(By.cssSelector("button[type='submit'].btn.btn-primary"));
        saveBtn.waitUntilVisible();
        saveBtn.waitUntilClickable();
        saveBtn.click();
        System.out.println("✓ Clicked Save button");
        waitABit(1000);
    }

    public void clickCancelButton() {
        WebElementFacade cancelBtn = find(By.cssSelector("a.btn.btn-secondary[href='/ui/categories']"));
        cancelBtn.waitUntilVisible();
        cancelBtn.waitUntilClickable();
        cancelBtn.click();
        System.out.println("✓ Clicked Cancel button");
        waitABit(1000);
    }

    public void isSaveButtonVisible() {
        saveButton.waitUntilVisible();
        saveButton.shouldBeVisible();
        saveButton.shouldContainText("Save");
        System.out.println("✓ Save button is visible");
    }

    public void isCancelButtonVisible() {
        cancelButton.waitUntilVisible();
        cancelButton.shouldBeVisible();
        cancelButton.shouldContainText("Cancel");
        System.out.println("✓ Cancel button is visible");
    }

    public void addNewCategory(String categoryName, String parentName) {
        System.out.println("Adding new category: " + categoryName + " with parent: "
                + (parentName == null || parentName.isEmpty() ? "None (Main Category)" : parentName));

        enterCategoryName(categoryName);

        if (parentName != null && !parentName.isEmpty()) {
            selectParentCategory(parentName);
        }

        clickSaveButton();

        // Wait for navigation back to categories page
        waitABit(1000);

        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("Current URL after adding category: " + currentUrl);
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Expected to be back on categories page but URL is: " + currentUrl);
        }
    }

    public void verifyAllAddCategoryPageComponents() {
        System.out.println("Starting verification of all add category page components...");

        isAddCategoryPageTitleVisible();
        isCategoryNameInputVisible();
        isParentCategorySelectVisible();
        isParentCategoryHelpTextVisible();
        isSaveButtonVisible();
        isCancelButtonVisible();

        System.out.println("✓ All add category page components verified successfully!");
    }

    public int getDisplayedRecordCount() {
        waitABit(500);

        List<WebElementFacade> tableRows = findAll("table.table tbody tr");

        // Filter out the "No category found" row
        long actualRows = tableRows.stream()
                .filter(row -> {
                    List<WebElementFacade> cells = row.thenFindAll("td");
                    // Check if it's the empty state row (has colspan attribute)
                    if (cells.size() == 1) {
                        String colspanValue = cells.get(0).getDomAttribute("colspan");
                        return colspanValue == null; // Only count rows without colspan
                    }
                    return true;
                })
                .count();

        System.out.println("📊 Displayed records: " + actualRows);
        return (int) actualRows;
    }

}