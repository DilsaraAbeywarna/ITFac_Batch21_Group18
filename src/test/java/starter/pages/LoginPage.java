package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:8080/ui/login")
public class LoginPage extends PageObject {

    // ==================== NAVIGATION METHODS ====================
    
    public void openPage() {
        getDriver().get("http://localhost:8080/ui/login");
    }

    // ==================== FORM ACTION METHODS ====================
    
    public void enterUsername(String username) {
        WebElementFacade usernameField = $(By.name("username"));
        usernameField.waitUntilVisible();
        usernameField.clear();
        usernameField.type(username);
    }

    public void enterPassword(String password) {
        WebElementFacade passwordField = $(By.name("password"));
        passwordField.waitUntilVisible();
        passwordField.clear();
        passwordField.type(password);
    }

    public void clickLogin() {
        WebElementFacade loginButton = $(By.cssSelector("button[type='submit']"));
        loginButton.waitUntilClickable();
        loginButton.click();
        waitABit(1000);
    }

    // ==================== VERIFICATION METHODS ====================
    
    public boolean isDashboardDisplayed() {
        try {
            waitABit(2000);
            String currentUrl = getDriver().getCurrentUrl();
            return currentUrl.contains("/ui/dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            WebElementFacade errorMessage = $(By.cssSelector("div.alert.alert-danger"));
            errorMessage.waitUntilVisible();
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void waitForSuccessfulLogin() {
        try {
            waitABit(1000);
            
            waitForCondition()
                .withTimeout(java.time.Duration.ofSeconds(10))
                .until(driver -> driver.getCurrentUrl().contains("/ui/dashboard"));
            
        } catch (Exception e) {
            // Login redirect timeout
        }
    }
}