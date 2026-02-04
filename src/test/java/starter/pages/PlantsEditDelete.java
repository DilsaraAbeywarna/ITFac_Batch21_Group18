package starter.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class PlantsEditDelete extends PageObject {

    // Dashboard → Manage Plants button
    @FindBy(css = "a[href='/ui/plants']")
    private WebElementFacade managePlantsButton;

    // First Edit button in Plants list
    @FindBy(css = "a[title='Edit']")
    private WebElementFacade firstEditButton;

    public void navigateToPlantsPage() {
        managePlantsButton.waitUntilClickable().click();
    }

    public void clickEditButton() {
        firstEditButton.waitUntilClickable().click();
    }

    public boolean isOnEditPlantPage() {
        return getDriver().getCurrentUrl().contains("/ui/plants/edit");
    }
}
