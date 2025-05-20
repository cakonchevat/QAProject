package org.example.pages.tabs;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ReminderHomePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ReminderHomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private final By reminderSelector = By.id("com.reminder.callreminder.phone:id/chMultiSelected");
    private final By selectAllButton = By.id("com.reminder.callreminder.phone:id/chAllSelected");
    private final By deleteButton = By.id("com.reminder.callreminder.phone:id/llDelete");
    private final By confirmDeletionButton = By.id("android:id/button1");


    public void tapAddReminderButton() {
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("com.reminder.callreminder.phone:id/sd_main_fab")));
        plusButton.click();
    }

    public void tapTaskReminder() {
        WebElement taskReminder = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Task Reminder")
        ));
        taskReminder.click();
    }

    public void navigateToTaskTab() {
        try {
            By homeIcon = By.xpath("(//android.widget.ImageView[@resource-id='com.reminder.callreminder.phone:id/nav_icon'])[1]");
            wait.until(ExpectedConditions.elementToBeClickable(homeIcon)).click();
            Thread.sleep(50);
        } catch (Exception ignored) {
            System.out.println("Already on home page or button not needed.");
        }
    }

    public void completeAllReminders() {
        while (true) {
            try {
                By firstTaskCheckBox = By.xpath("(//android.widget.CheckBox[@resource-id='com.reminder.callreminder.phone:id/ivCompleted'])[1]");
                WebElement box = wait.until(ExpectedConditions.elementToBeClickable(firstTaskCheckBox));
                box.click();
                System.out.println("Completed one reminder.");
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("No more reminders to complete.");
                break;
            }
        }
    }

    private void longPressElement(WebElement element) {
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

    public void longPressOnTaskByIndex(int index) {
        try {
            String xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']/android.view.ViewGroup[" + index + "]";
            WebElement task = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            longPressElement(task);
        } catch (Exception e) {
            System.out.println("Task with index " + index + " not found or could not be long-pressed.");
        }
    }

    public void deleteAllReminders() {
        try {
            List<WebElement> reminders = driver.findElements(reminderSelector);

            if (reminders.isEmpty()) {
                System.out.println("No reminders found. Nothing to delete.");
                return;
            }

            if (reminders.size() > 1) {
                WebElement selectAll = wait.until(ExpectedConditions.elementToBeClickable(selectAllButton));
                selectAll.click();
                System.out.println("Selected all reminders.");
            }

            WebElement delete = wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
            delete.click();
            System.out.println("Clicked delete button.");

            // Pause briefly to allow dialog to appear
            Thread.sleep(500);

            List<WebElement> confirmButtons = driver.findElements(confirmDeletionButton);
            if (!confirmButtons.isEmpty()) {
                WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButtons.get(0)));
                confirm.click();
                System.out.println("Confirmation dialog clicked. Reminders deleted.");
            } else {
                System.out.println("No confirmation dialog appeared. Deletion may be completed automatically.");
            }

        } catch (Exception e) {
            System.out.println("Error during reminder deletion: " + e.getMessage());
        }
    }


}
