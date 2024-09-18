package helper;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.events.WebDriverListener;
import utility.MyLogger;
import utility.MyReport;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class SeleniumEventListener implements WebDriverListener {
    private static final String SCREENSHOT_DIR = "output/screenshots/";
    private WebDriver driver;

    public SeleniumEventListener(WebDriver driver) {
        this.driver = driver;
    }
    @Override
    public void beforeClick(WebElement element) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Clicking on element [" + element + "]");

    }

    @Override
    public void afterClick(WebElement element) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully clicked on element [" + element + "]",(TakesScreenshot) driver);
        MyLogger.info(this.getClass().getSimpleName(), "After clicking on element: " + element);
        captureScreenshot("after_click");
    }

    @Override
    public void beforeTo(WebDriver.Navigation navigation, String url) {
        MyReport.startStep(url, this.getClass().getSimpleName(), "Navigating to " + url);
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        MyReport.updateStepToBePassed(url, this.getClass().getSimpleName(),
                "Navigated to " + url + " successfully",(TakesScreenshot) driver);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        MyReport.startStep(locator.toString(), this.getClass().getSimpleName(),
                "Finding element [" + locator + "]");
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        MyReport.updateStepToBePassed(locator.toString(), this.getClass().getSimpleName(),
                "Successfully found element [" + locator + "]",(TakesScreenshot) driver);
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        MyReport.startStep(locator.toString(), this.getClass().getSimpleName(),
                "Finding elements [" + locator + "]");
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        MyReport.updateStepToBePassed(locator.toString(), this.getClass().getSimpleName(),
                "Successfully found elements [" + locator + "]",(TakesScreenshot) driver);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Sending: " + Arrays.toString(keysToSend) + " to element [" + element + "]");
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully sending text to [" + element + "]",(TakesScreenshot) driver);
        MyLogger.info(this.getClass().getSimpleName(), "After send text to element: " + element);
        captureScreenshot("after_click");
    }

    @Override
    public void beforeGetText(WebElement element) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Getting text of element [" + element + "]");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully got: " + result + " as the text of the element [" + element + "]",(TakesScreenshot) driver);
    }

    private void captureScreenshot(String actionName) {
        var timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        File srcFile = screenshotDriver.getScreenshotAs(OutputType.FILE);

        String screenshotPath = SCREENSHOT_DIR + actionName + "_" + timestamp + ".png";
        File screenshotFile = new File(screenshotPath);

        try {
            FileHandler.copy(srcFile, screenshotFile);
            MyLogger.info(this.getClass().getSimpleName(), "Screenshot captured: " + screenshotPath);
        } catch (IOException e) {
            MyLogger.severe(this.getClass().getSimpleName(), "Failed to capture screenshot.");
            e.printStackTrace();
        }
    }

}
