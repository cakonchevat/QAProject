package org.example.pages.tabs;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.example.pages.tabs.CommonPageElements.*;

public class ReminderHomePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ReminderHomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void completeReminder(String title) {
        try {
            // пронаоѓање на карта со тој наслов
            By card = By.xpath(
                    "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']" +
                            "/android.view.ViewGroup[" +
                            ".//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvEventTitle' and @text='" + title + "']" +
                            "]"
            );
            WebElement cardElement = wait.until(ExpectedConditions.presenceOfElementLocated(card));

            // во најдената карта, пронаоѓање на елемент checkbox и негов клик
            WebElement completeCheckbox = cardElement.findElement(By.id("com.reminder.callreminder.phone:id/ivCompleted"));
            completeCheckbox.click();

            System.out.println("Reminder with title \"" + title + "\" marked as completed.");
        } catch (Exception e) {
            System.out.println("Failed to complete reminder with title \"" + title + "\": " + e.getMessage());
        }
    }

    public void deleteAllReminders() {
        try {
            List<WebElement> reminders = driver.findElements(SELECTED_ELEMENT_CHECK);

            if (reminders.isEmpty()) {
                System.out.println("No reminders found. Nothing to delete.");
                return;
            }

            if (reminders.size() > 1) {
                WebElement selectAll = wait.until(ExpectedConditions.elementToBeClickable(SELECT_ALL_BUTTON));
                selectAll.click();
                System.out.println("Selected all reminders.");
            }

            WebElement delete = wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
            delete.click();
            System.out.println("Clicked delete button.");

            Thread.sleep(500);

            List<WebElement> confirmButtons = driver.findElements(CONFIRM_DELETE_BUTTON);
            if (!confirmButtons.isEmpty()) {
                WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmButtons.get(0)));
                confirm.click();
                System.out.println("Confirmation dialog clicked. Reminders deleted.");
            } else {
                System.out.println("No confirmation dialog appeared. Deletion may be completed automatically.");
            }

            Thread.sleep(1000);
            List<WebElement> postDeleteCheck = driver.findElements(SELECTED_ELEMENT_CHECK);
            if (postDeleteCheck.isEmpty()) {
                System.out.println("All reminders have been successfully deleted.");
            } else {
                System.out.println("Some reminders may still remain after deletion.");
            }

        } catch (Exception e) {
            System.out.println("Error during reminder deletion: " + e.getMessage());
        }
    }


    public static String convertToDisplayDateTime(String dateText, String hourLabel, String minuteLabel) throws ParseException {
        String fixedYear = "2025";
        String fullDateText = dateText + ", " + fixedYear;
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        Date parsedDate = inputDateFormat.parse(fullDateText);
        // парсирање на датумот во посакуван излез, пример: Wed,18 Jun
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEE,dd MMM", Locale.ENGLISH);
        String formattedDate = outputDateFormat.format(parsedDate);

        // парсирање на саатот и минути во соодветен формат за пребарување, пример: 09:00
        int hour = Integer.parseInt(hourLabel.replace(" o'clock", ""));
        String minute = minuteLabel.replace(" minutes", "");

        return formattedDate + " " + String.format("%02d:%s", hour, minute);
    }


    public void checkExistenceOfTaskReminder(String title, String dateText, String hourLabel, String minuteLabel) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        String formattedTime;
        try {
            formattedTime = convertToDisplayDateTime(dateText, hourLabel, minuteLabel);
        } catch (Exception e) {
            System.out.println("Failed to format date/time: " + e.getMessage());
            return;
        }

        By reminderCard = By.xpath(
                "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']" +
                        "/android.view.ViewGroup[" +
                        ".//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvEventTitle' and @text='" + title + "']" +
                        " and " +
                        ".//android.widget.TextView[@resource-id='com.reminder.callreminder.phone:id/tvEventTime' and @text='" + formattedTime + "']" +
                        "]"
        );

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(reminderCard));
            System.out.println("Reminder with title \"" + title + "\" and time \"" + formattedTime + "\" is visible.");
        } catch (Exception e) {
            System.out.println("Reminder with title \"" + title + "\" and time \"" + formattedTime + "\" not visible.");
        }

    }

}
