package page;

import helper.actions.GuiAction;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutPage {
    private final WebDriver driver;
    private final GuiAction guiAction;

    private final By checkoutBtn = By.xpath("//button[@data-hook='CheckoutButtonDataHook.button']");
    private final By totalSumText = By.id("total-sum");

    @Step("Verify the Total Price is Right")
    public CheckoutPage assertTheTotalPriceIsRight(String expectedPrice){
        guiAction.waitForLazyLoading(checkoutBtn);
        guiAction.assertThat("Assert the Total Price is "+expectedPrice,
                () -> assertTrue(guiAction.getTextFrom(totalSumText).contentEquals(expectedPrice)));

        return this;

    }

    public void ClickOnCheckOut(){
        guiAction.clickOn(checkoutBtn);

    }

    public CheckoutPage (WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }
}
