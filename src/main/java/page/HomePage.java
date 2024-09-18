package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    private final GuiAction guiAction;

    private final By shopBtn = By.id("comp-iy4cwgmq1");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }

   public ShopPage ClickOnShop(){
       guiAction.clickOn(shopBtn);
        return new ShopPage(driver);

   }
}
