package hooks;

import io.cucumber.java.Before;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;
import starter.pages.LoginPage;

public class TestHooks {

    @Managed
    WebDriver driver;

    // ===============================
    // ADMIN LOGIN
    // ===============================
    @Before("@admin")
    public void loginAsAdmin() {
        System.out.println("called admin hook");
        authenticate("admin", "admin123");
    }

    // ===============================
    // NON ADMIN LOGIN
    // ===============================
    @Before("@nonadmin")
    public void loginAsNonAdmin() {
        System.out.println("called nonadmin hook");
        authenticate("testuser", "test123");
    }

    // ===============================
    // COMMON AUTH METHOD
    // ===============================
    private void authenticate(String username, String password) {
        LoginPage loginPage = new LoginPage();
        loginPage.setDriver(driver);
        
        // Navigate and login
        loginPage.openPage();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        
        // Verify dashboard is displayed
        if (!loginPage.isDashboardDisplayed()) {
            throw new RuntimeException("Dashboard did not load after login for: " + username);
        }
        
        System.out.println("Successfully logged in as: " + username);
        
        // Additional wait to ensure page is fully loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}