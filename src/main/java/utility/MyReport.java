package utility;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

public class MyReport {
    private MyReport() {
    }

    public static void startStep(String uuid, String title, String msg) {
        MyLogger.info(title, msg);
        Allure.getLifecycle().startStep(uuid, new StepResult().setName(msg).setStatus(Status.FAILED));
    }

    public static String startStep(String title, String msg) {
        var uuid = UUID.randomUUID().toString();
        startStep(uuid, title, msg);

        return uuid;
    }

    public static void updateStepStatus(String uuid, Status status) {
        Allure.getLifecycle().updateStep(uuid, step -> step.setStatus(status));
    }

    public static void stopStep(String uuid) {
        Allure.getLifecycle().stopStep(uuid);
    }

    public static void stopStep(String uuid, String title, String msg) {
        MyLogger.info(title, msg);
        stopStep(uuid);
    }

    public static void updateStepToBePassed(String uuid) {
        updateStepStatus(uuid, Status.PASSED);
        stopStep(uuid);

    }

    public static void updateStepToBePassed(String uuid, String title, String msg, TakesScreenshot driver) {
        MyLogger.info(title, msg);
        updateStepToBePassed(uuid);
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        try (InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(screenshot.toPath()))) {
            // Attach the screenshot to the Allure report
            attachScreenshot(inputStream);
        } catch (Exception e) {
            MyLogger.severe("Failed to attach screenshot",e.toString());
        }

        Allure.addAttachment(msg, title);  // You may also want to keep this if required
    }


    public static void attach(String title, String att) {
        MyLogger.info(title, att);
        Allure.addAttachment("Response", att);
    }

    public static void attachScreenshot(InputStream inputStream) {
        MyLogger.info(MyLogger.class.getSimpleName(), "Attaching Screenshot to the Allure report");
        Allure.addAttachment("screenshot", inputStream);
    }
}