package helper;

import org.apache.commons.io.FileUtils;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import utility.MyLogger;
import utility.MyReport;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static helper.Constant.TEST_RESOURCES_PATH;

public class JunitListener implements TestExecutionListener {

    private static final String SCREENSHOT_DIR = "output/screenshots/";

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (testIdentifier.getType().isTest()) {
            MyLogger.info(this.getClass().getSimpleName(),
                    "Test started: " + testIdentifier.getDisplayName());
            cleanScreenshotsDirectory();
        }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.getType().isTest()) {
            MyLogger.info(this.getClass().getSimpleName(),
                    "Test finished: " + testIdentifier.getDisplayName() +
                            " with status: " + testExecutionResult.getStatus());
            captureScreenshot(testIdentifier, testExecutionResult);
        }
    }

    private void captureScreenshot(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        TakesScreenshot driver = (TakesScreenshot) WebDriverFactory.getDriver(
                new PropertyReader(TEST_RESOURCES_PATH + "configuration/browser-config"));

        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        String screenshotPath = SCREENSHOT_DIR + testIdentifier.getDisplayName() + "_" + timestamp + ".png";
        File screenshotFile = new File(screenshotPath);

        try {
            createDirectoryIfNotExists(SCREENSHOT_DIR);
            FileHandler.copy(srcFile, screenshotFile);

            MyReport.attachScreenshot(FileUtils.openInputStream(screenshotFile));
            MyLogger.info(this.getClass().getSimpleName(),
                    "Screenshot captured for test: " + testIdentifier.getDisplayName() +
                            " at: " + screenshotPath);
        } catch (IOException e) {
            MyLogger.severe(this.getClass().getSimpleName(),
                    "Failed to capture screenshot for test: " + testIdentifier.getDisplayName());
            e.printStackTrace();
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                MyLogger.info(this.getClass().getSimpleName(),
                        "Created screenshot directory: " + directoryPath);
            } else {
                MyLogger.warning(this.getClass().getSimpleName(),
                        "Failed to create screenshot directory: " + directoryPath);
            }
        }
    }

    private void cleanScreenshotsDirectory() {
        File directory = new File(SCREENSHOT_DIR);
        if (directory.exists() && directory.isDirectory()) {
            try {
                FileUtils.cleanDirectory(directory);
                MyLogger.info(this.getClass().getSimpleName(),
                        "Cleaned screenshot directory: " + SCREENSHOT_DIR);
            } catch (IOException e) {
                MyLogger.severe(this.getClass().getSimpleName(),
                        "Failed to clean screenshot directory: " + SCREENSHOT_DIR);
                e.printStackTrace();
            }
        }
    }
}
