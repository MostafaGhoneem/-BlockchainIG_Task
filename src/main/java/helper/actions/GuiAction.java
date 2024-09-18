package helper.actions;

import helper.Constant;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;
import utility.MyReport;
import java.time.Duration;

import java.util.function.BiFunction;


public class GuiAction {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;



    private final BiFunction<WebDriverWait, WebElement, WebElement> waitVisibilityOf = (wait, element) ->
            wait.until(ExpectedConditions.visibilityOf(element));
    private final BiFunction<WebDriverWait, By, WebElement> waitPresenceOf = (wait, by) ->
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

    public GuiAction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.getWaitTime()));
        actions = new Actions(driver);
    }


    public WebElement waitForVisibility(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return element;
    }

    public void clickOn(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void hoverTo(By by){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(by)).perform();

    }

    public void switchToIFrameByElement(By by){
        WebElement iframe = driver.findElement(by);
        driver.switchTo().frame(iframe);


    }

    public void switchToDefaultIframe(){

        driver.switchTo().defaultContent();
    }

    public void clickOn(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        clickOn(driver.findElement(by));
    }

    public void sendTextTo(WebElement element, String text) {
        waitVisibilityOf.apply(wait, element);
        element.clear();
        element.sendKeys(text);
    }

    public void sendTextTo(By by, String text) {
        waitPresenceOf.apply(wait, by);
        sendTextTo(driver.findElement(by), text);
    }


    public String getTextFrom(WebElement element) {
        return element.getText();
    }

    public String getTextFrom(By by) {
        waitPresenceOf.apply(wait, by);
        return getTextFrom(driver.findElement(by));
    }



    public void waitForLazyLoading(By by){
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
         wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        }



    public void assertThat(String message, Runnable runnable) {
        var stepId = MyReport.startStep(this.getClass().getSimpleName(), message);

        try {
            runnable.run();
            MyReport.updateStepToBePassed(stepId);
        } catch (AssertionFailedError e){
            MyReport.stopStep(stepId);
            throw e;
        }
    }



    }

