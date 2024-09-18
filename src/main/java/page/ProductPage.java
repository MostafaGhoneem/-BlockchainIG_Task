package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductPage {

    private final WebDriver driver;
    private final GuiAction guiAction;




    private final By addToCartBtn = By.xpath("//button[@data-hook='add-to-cart']");
    private final By firstColorRadioBtn = By.xpath("//div[@data-hook='color-picker-item']");
    private final By quantityInput = By.xpath("//div[@data-hook='number-input-spinner-container']");
    private final By arrowUpQuantityBtn = By.xpath("//span[@data-hook='number-input-spinner-up-arrow']");
    private final By arrowDownQuantityBtn = By.xpath("//span[@data-hook='number-input-spinner-down-arrow']");
    private final By viewCartBtn = By.xpath("//a[@data-hook='widget-view-cart-button']");
    private final By iFrameElement = By.cssSelector("iframe[src*='cartwidgetPopup']");


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }

    public ProductPage PickColor() {
        guiAction.waitForLazyLoading(firstColorRadioBtn);
        guiAction.clickOn(firstColorRadioBtn);

        return this;
    }

    public ProductPage AddQuantity(){
        guiAction.hoverTo(quantityInput);
        guiAction.clickOn(arrowUpQuantityBtn);
        guiAction.clickOn(arrowUpQuantityBtn);

        return this;

    }

    public ProductPage addToCart(){

        guiAction.clickOn(addToCartBtn);

        return this;

    }

    public CheckoutPage openCart() {
        guiAction.switchToIFrameByElement(iFrameElement);
        guiAction.waitForLazyLoading(viewCartBtn);
        guiAction.clickOn(viewCartBtn);
        guiAction.switchToDefaultIframe();

        return new CheckoutPage(driver);

    }


}
