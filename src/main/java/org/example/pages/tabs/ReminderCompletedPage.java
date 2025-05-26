package org.example.pages.tabs;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class ReminderCompletedPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ReminderCompletedPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void checkExistenceOfCompletedReminder(String title) {
        try {
            By completedReminderCard = By.xpath(
                    "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvComplete']" +
                            "/android.view.ViewGroup[.//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvEventTitle' and @text='" + title + "']]"
            );

            wait.until(ExpectedConditions.visibilityOfElementLocated(completedReminderCard));
            System.out.println("Reminder with title \"" + title + "\" found in the Completed tab.");
        } catch (Exception e) {
            System.out.println("Reminder with title \"" + title + "\" was NOT found in the Completed tab.");
        }
    }

}
