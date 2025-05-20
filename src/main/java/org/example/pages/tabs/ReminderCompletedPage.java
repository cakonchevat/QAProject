package org.example.pages.tabs;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class ReminderCompletedPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public ReminderCompletedPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    public void navigateToCompletedTab() {
        try {
            By completedTab = By.xpath("(//android.widget.ImageView[@resource-id='com.reminder.callreminder.phone:id/nav_icon'])[3]");
            wait.until(ExpectedConditions.elementToBeClickable(completedTab)).click();
            Thread.sleep(100);
            System.out.println("Navigated to completed tab.");
        } catch (Exception e) {
            System.out.println("Failed to navigate to completed tab.");
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

    public void selectSomeRemindersToDelete(int start, int end) {
        if (end < start) {
            System.out.println("Invalid range: end index must be greater than or equal to start index.");
            return;
        }

        for (int i = start; i <= end; i++) {
            try {
                By checkbox = By.xpath("(//android.widget.CheckBox[@resource-id='com.reminder.callreminder.phone:id/chMultiSelected'])[" + i + "]");
                WebElement box = wait.until(ExpectedConditions.elementToBeClickable(checkbox));
                box.click();
                System.out.println("Selected completed reminder checkbox at index " + i);
            } catch (Exception e) {
                System.out.println("No checkbox found or clickable at index " + i);
            }
        }
    }

    public void confirmDeletionButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("com.reminder.callreminder.phone:id/llDelete2"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))).click();
            System.out.println("Selected completed reminders deleted.");
        } catch (Exception e) {
            System.out.println("Delete button for completed reminders not found.");
        }
    }

}
