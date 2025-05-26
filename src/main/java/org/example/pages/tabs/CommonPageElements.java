package org.example.pages.tabs;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;


public class CommonPageElements {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CommonPageElements(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private static final By HOME_TAB = By.xpath("(//android.widget.ImageView[@resource-id='com.reminder.callreminder.phone:id/nav_icon'])[1]");
    public static final By CALL_TAB = By.xpath("(//android.widget.ImageView[@resource-id='com.reminder.callreminder.phone:id/nav_icon'])[2]");
    public static final By COMPLETED_TAB = By.xpath("(//android.widget.ImageView[@resource-id='com.reminder.callreminder.phone:id/nav_icon'])[3]");

    private static final By CREATE_TASK_REMINDER = AppiumBy.accessibilityId("Task Reminder");
    private static final By CREATE_CALL_REMINDER = AppiumBy.accessibilityId("Call Reminder");

    public static final By PLUS_BUTTON = By.id("com.reminder.callreminder.phone:id/sd_main_fab");
    public static final By DELETE_BUTTON = By.id("com.reminder.callreminder.phone:id/llDelete");
    public static final By CONFIRM_DELETE_BUTTON = By.id("android:id/button1");
    public static final By SELECT_ALL_BUTTON = By.id("com.reminder.callreminder.phone:id/chAllSelected");
    public static final By SELECTED_ELEMENT_CHECK = By.id("com.reminder.callreminder.phone:id/chMultiSelected");

    public void navigateToTab(String tabName) {
        try {
            if (tabName.equals("home")) {
                wait.until(ExpectedConditions.elementToBeClickable(HOME_TAB)).click();
                Thread.sleep(50);
            }
            else if (tabName.equals("call")) {
                wait.until(ExpectedConditions.elementToBeClickable(CALL_TAB)).click();
                Thread.sleep(50);
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(COMPLETED_TAB)).click();
                Thread.sleep(50);
            }
        } catch (Exception ignored) {
            System.out.println("Already on that page.");
        }
    }

    public void startReminderCreation(String type_reminder) {
        wait.until(ExpectedConditions.elementToBeClickable(PLUS_BUTTON)).click();
        if(type_reminder.equals("task")) {
            wait.until(ExpectedConditions.elementToBeClickable(CREATE_TASK_REMINDER)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(CREATE_CALL_REMINDER)).click();
        }
    }

    public static void longPressElement(AndroidDriver driver, WebElement element) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        int centerX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int centerY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        Sequence longPress = new Sequence(finger, 1);
        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofSeconds(2)));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(longPress));
    }

    public void longPressOnReminderByIndex(int index, String type_reminder) {
        try {
            if (type_reminder.equals("task")) {
                String xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']/android.view.ViewGroup[" + index + "]";
                WebElement task = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                longPressElement(driver, task);
            } else {
                String xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvCallReminder']/android.view.ViewGroup[" + index + "]";
                WebElement task = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                longPressElement(driver, task);
            }
        } catch (Exception e) {
            System.out.println("Task with index " + index + " not found or could not be long-pressed.");
        }
    }

}
