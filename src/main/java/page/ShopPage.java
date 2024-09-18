package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ShopPage {
    private final WebDriver driver;
    private final GuiAction guiAction;

    private final By bestSellerProduct = By.xpath(
            "(//*[contains(@aria-label, 'Best Seller gallery')]//div[@data-hook='product-item-product-details'])"
    );



    public ShopPage(WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }

    public ProductPage ClickOnBestSellerProduct() {

        guiAction.waitForVisibility(driver.findElement(bestSellerProduct));
        guiAction.clickOn(bestSellerProduct);

        return new ProductPage(driver);

    }
}
