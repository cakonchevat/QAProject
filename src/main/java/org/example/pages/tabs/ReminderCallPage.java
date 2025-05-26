package org.example.pages.tabs;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.example.pages.tabs.CommonPageElements.*;

import java.time.Duration;
import java.util.List;

public class ReminderCallPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ReminderCallPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private static final By SELECT_CALL_REMINDER = By.id("com.reminder.callreminder.phone:id/rvCallReminder");

    public void deleteReminder(String callee, String title) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(SELECT_CALL_REMINDER));
            Thread.sleep(500);

            wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
            Thread.sleep(300);

            List<WebElement> confirmButtons = driver.findElements(CONFIRM_DELETE_BUTTON);
            if (!confirmButtons.isEmpty()) {
                WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButtons.get(0)));
                confirm.click();
                System.out.println("Reminder with title '" + title + "' and callee '" + callee + "' deleted.");
            } else {
                System.out.println("No confirmation dialog appeared.");
            }


        } catch (Exception e) {
            System.out.println("Error during reminder deletion: " + e.getMessage());
        }
    }

    public void checkExistenceOfCallReminder(String callee, String title, Boolean toDelete) {
        try {
            Thread.sleep(1500); // Let reminders load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        By reminderCard = By.xpath(
                "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvCallReminder']" +
                        "/android.view.ViewGroup[" +
                        ".//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvContactName' and @text='" + callee + "']" +
                        " and " +
                        ".//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvEventTitle' and @text='" + title + "']" +
                        "]"
        );

        try {
            WebElement cardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(reminderCard));
            System.out.println("Reminder with callee \"" + callee + "\" and title \"" + title + "\" is visible.");

            if (toDelete) {
                // Use your predefined long press method
                longPressElement(driver, cardElement);
                System.out.println("Performed long press on the reminder.");

                // Click delete button
                WebElement delete = wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
                delete.click();
                System.out.println("Clicked delete button.");

                // Confirm deletion
                List<WebElement> confirmButtons = driver.findElements(CONFIRM_DELETE_BUTTON);
                if (!confirmButtons.isEmpty()) {
                    WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButtons.get(0)));
                    confirm.click();
                    System.out.println("Confirmed deletion.");
                } else {
                    System.out.println("No confirmation dialog appeared.");
                }
            }

        } catch (Exception e) {
            System.out.println("Reminder with callee \"" + callee + "\" and title \"" + title + "\" not found or could not be deleted: " + e.getMessage());
        }
    }


}
