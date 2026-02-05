package starter.pages.Category;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.time.Duration;
import java.util.List;

@DefaultUrl("/ui/categories")
public class CategoryPage extends PageObject {

    // Sidebar navigation link to Categories page
    @FindBy(css = "a[href='/ui/categories']")
    public WebElementFacade categoriesNavLink;

    // Add A Category button
    @FindBy(css = "a[href='/ui/categories/add'].btn.btn-primary")
    public WebElementFacade addCategoryButton;

    // Alternative locator for Add A Category button
    @FindBy(xpath = "//a[@href='/ui/categories/add' and contains(@class, 'btn-primary')]")
    public WebElementFacade addCategoryButtonAlt;

    // Page heading/title
    @FindBy(css = "h1, h2")
    public WebElementFacade pageHeading;

    // Category list table/container
    @FindBy(css = "table, .category-list, .table")
    public WebElementFacade categoryListContainer;

    // Success message
    @FindBy(css = ".alert-success, .alert.alert-success")
    public WebElementFacade successMessage;

    // Edit button/icon for any category in the list
    @FindBy(css = "a[href*='/ui/categories/edit/'], button[class*='edit'], .edit-action")
    public List<WebElementFacade> editActionButtons;

    public void openPage() {
        getDriver().get("http://localhost:8080/ui/categories");
        System.out.println("Explicitly navigated to: " + getDriver().getCurrentUrl());
    }

    public void navigateToCategoriesPageViaSidebar() {
        categoriesNavLink.waitUntilClickable().click();
        waitABit(1000);
        System.out.println("Clicked Categories sidebar link. Current URL: " + getDriver().getCurrentUrl());
    }

    public boolean isCategoryListPageDisplayed() {
        try {
            boolean urlCorrect = getDriver().getCurrentUrl().contains("/ui/categories")
                    && !getDriver().getCurrentUrl().contains("/ui/categories/add")
                    && !getDriver().getCurrentUrl().contains("/ui/categories/edit");

            boolean headingVisible = pageHeading.isVisible();

            System.out.println("Category list page displayed - URL correct: " + urlCorrect
                    + ", Heading visible: " + headingVisible);

            return urlCorrect && headingVisible;
        } catch (Exception e) {
            System.out.println("Error checking category list page: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddCategoryButtonVisible() {
        try {
            addCategoryButton.waitUntilVisible();
            boolean isVisible = addCategoryButton.isVisible();
            System.out.println("Add A Category button visibility: " + isVisible);
            return isVisible;
        } catch (Exception e) {
            System.out.println("Add A Category button not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddCategoryButtonClickable() {
        try {
            addCategoryButton.waitUntilClickable();
            boolean isClickable = addCategoryButton.isClickable();
            System.out.println("Add A Category button clickable: " + isClickable);
            return isClickable;
        } catch (Exception e) {
            System.out.println("Add A Category button not clickable: " + e.getMessage());
            return false;
        }
    }

    public void clickAddCategoryButton() {
        addCategoryButton.waitUntilClickable().click();
        waitABit(1000);
        System.out.println("Clicked Add Category button. Current URL: " + getDriver().getCurrentUrl());
    }

    public boolean isOnCategoryListPage() {
        System.out.println("Waiting for Category List page...");
        System.out.println("Current URL before wait: " + getDriver().getCurrentUrl());

        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(driver -> {
                        String currentUrl = driver.getCurrentUrl();
                        System.out.println("Checking URL: " + currentUrl);
                        return currentUrl.contains("/ui/categories")
                                && !currentUrl.contains("/ui/categories/add")
                                && !currentUrl.contains("/ui/categories/edit");
                    });
            return true;
        } catch (Exception e) {
            System.out.println("Failed to verify category list page. Final URL: " + getDriver().getCurrentUrl());
            throw e;
        }
    }

    public boolean isCategoryInList(String categoryName) {
        try {
            waitABit(1000);
            String pageSource = getDriver().getPageSource();
            boolean categoryExists = pageSource.contains(categoryName);
            System.out.println("Category '" + categoryName + "' in list: " + categoryExists);
            return categoryExists;
        } catch (Exception e) {
            System.out.println("Error checking if category is in list: " + e.getMessage());
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            successMessage.waitUntilVisible();
            boolean isVisible = successMessage.isVisible();
            System.out.println("Success message visibility: " + isVisible);
            if (isVisible) {
                System.out.println("Success message text: " + successMessage.getText());
            }
            return isVisible;
        } catch (Exception e) {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }
    }

    public String getPageHeading() {
        return pageHeading.getText();
    }

    public void clickEditIconForCategory(String categoryName) {
        try {
            String xpath = "//tr[.//td[contains(normalize-space(.), '" + categoryName
                    + "')]]//a[contains(@href, '/ui/categories/edit') and (contains(@title, 'Edit') or contains(@class,'btn'))]";
            WebElementFacade editLink = findBy(xpath);
            editLink.waitUntilClickable().click();
            waitABit(1000);
            System.out.println("Clicked Edit icon for category: " + categoryName + ". Current URL: "
                    + getDriver().getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Failed to click Edit icon for category '" + categoryName + "': " + e.getMessage());
            throw e;
        }
    }

    public void verifyOnCategoryPage() {
        pageHeading.shouldBeVisible();
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Expected to be on category page but URL is: " + currentUrl);
        }
    }

    public void verifyPageTitleContainsText(String text) {
        pageHeading.shouldBeVisible();
        pageHeading.shouldContainText(text);
    }

    public void verifyAddCategoryButtonVisible() {
        addCategoryButton.shouldBeVisible();
    }

    public boolean areEditButtonsVisible() {
        try {
            waitABit(1000);
            // Check if any edit buttons exist and are visible
            boolean hasVisibleButtons = !editActionButtons.isEmpty() &&
                    editActionButtons.stream().anyMatch(WebElementFacade::isVisible);

            System.out.println("Edit buttons check - Total found: " + editActionButtons.size() +
                    ", Visible: " + hasVisibleButtons);
            return hasVisibleButtons;
        } catch (Exception e) {
            System.out.println("No edit buttons found or error checking: " + e.getMessage());
            return false;
        }
    }

    public int getVisibleEditButtonsCount() {
        try {
            long count = editActionButtons.stream()
                    .filter(WebElementFacade::isVisible)
                    .count();
            System.out.println("Visible edit buttons count: " + count);
            return (int) count;
        } catch (Exception e) {
            System.out.println("Error counting edit buttons: " + e.getMessage());
            return 0;
        }
    }

    public boolean areEditButtonsClickable() {
        try {
            boolean anyClickable = editActionButtons.stream()
                    .anyMatch(button -> button.isVisible() && button.isClickable());
            System.out.println("Edit buttons clickable: " + anyClickable);
            return anyClickable;
        } catch (Exception e) {
            System.out.println("Error checking if edit buttons are clickable: " + e.getMessage());
            return false;
        }
    }

    public boolean areEditButtonsDisabled() {
        try {
            waitABit(500);
            for (WebElementFacade button : editActionButtons) {
                if (button.isVisible()) {
                    // Check if button has 'disabled' attribute or class
                    String disabledAttr = button.getAttribute("disabled");
                    String classAttr = button.getAttribute("class");
                    boolean isDisabled = (disabledAttr != null && !disabledAttr.isEmpty()) ||
                            (classAttr != null && classAttr.contains("disabled"));

                    System.out.println("Edit button found - Visible: true, Disabled: " + isDisabled);

                    if (isDisabled) {
                        return true;
                    }
                }
            }
            System.out.println("No disabled edit buttons found");
            return false;
        } catch (Exception e) {
            System.out.println("Error checking if edit buttons are disabled: " + e.getMessage());
            return false;
        }
    }

    public String getEditButtonState() {
        try {
            waitABit(1000);

            if (editActionButtons.isEmpty()) {
                System.out.println("Edit button state: HIDDEN (no buttons found)");
                return "hidden";
            }

            boolean anyVisible = false;
            boolean anyEnabled = false;

            for (WebElementFacade button : editActionButtons) {
                if (button.isVisible()) {
                    anyVisible = true;

                    // Check if disabled
                    String disabledAttr = button.getAttribute("disabled");
                    String classAttr = button.getAttribute("class");
                    boolean isDisabled = (disabledAttr != null && !disabledAttr.isEmpty()) ||
                            (classAttr != null && classAttr.contains("disabled"));

                    if (!isDisabled && button.isEnabled()) {
                        anyEnabled = true;
                        break;
                    }
                }
            }

            String state;
            if (!anyVisible) {
                state = "hidden";
            } else if (anyVisible && !anyEnabled) {
                state = "disabled";
            } else {
                state = "enabled";
            }

            System.out.println("Edit button state: " + state.toUpperCase());
            return state;

        } catch (Exception e) {
            System.out.println("Error getting edit button state: " + e.getMessage());
            return "hidden";
        }
    }
}