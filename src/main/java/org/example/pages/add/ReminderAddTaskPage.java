package org.example.pages.add;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.appium.java_client.AppiumBy.accessibilityId;

public class ReminderAddTaskPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By addTitle = By.id("com.reminder.callreminder.phone:id/edTitle");
    private final By addSubtitle = By.id("com.reminder.callreminder.phone:id/etItem");
    private final By addDate = By.id("com.reminder.callreminder.phone:id/swSetTime");
    private final By saveReminder = By.id("com.reminder.callreminder.phone:id/ivDone");
    private final By editButton = By.id("com.reminder.callreminder.phone:id/ivEdit");
    private final By updateConfirmButton = By.id("android:id/button1");

    // Датум-ориентирани
    private final By startDateField = By.id("com.reminder.callreminder.phone:id/tvStartDate");
    private final By dateConfirmButton = By.id("com.reminder.callreminder.phone:id/confirm_button");

    // Време-ориентирани
    private final By startTimeField = By.id("com.reminder.callreminder.phone:id/tvStartTime");
    private final By timeConfirmButton = By.id("com.reminder.callreminder.phone:id/material_timepicker_ok_button");


    public ReminderAddTaskPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    public void setAddTitle(String title, String subtitle) {
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(addTitle));
        titleField.clear();
        titleField.sendKeys(title);

        // Додавање на subtitle (optional, but done to explore more functionalities)
        By subtitleButton = By.id("com.reminder.callreminder.phone:id/ivAddChak");
        wait.until(ExpectedConditions.elementToBeClickable(subtitleButton)).click();

        WebElement subtitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(addSubtitle));
        subtitleField.clear();
        subtitleField.sendKeys(subtitle);
    }

    public void setUpdateTitle(String title, String subtitle) {
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(addTitle));
        titleField.clear();
        titleField.sendKeys(title);

        WebElement subtitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(addSubtitle));
        subtitleField.clear();
        subtitleField.sendKeys(subtitle);
    }

    public void setAddDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(addDate)).click();
        wait.until(ExpectedConditions.elementToBeClickable(startDateField)).click();

        // Промена на месец (scroll functionality checked)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accessibilityId("Change to next month"))).click();
        } catch (Exception ignored) {
            System.out.println("Already on the correct month.");
        }

        // Селекција на тој датум
        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId(date))).click();
        wait.until(ExpectedConditions.elementToBeClickable(dateConfirmButton)).click();

        // Клик на часовник за понатамошен избор
        wait.until(ExpectedConditions.elementToBeClickable(startTimeField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"8 o'clock\"]")
        )).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"00 minutes\"]")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(timeConfirmButton)).click();
    }

    // ****************************
    public void setReminderRepeat() {
        // click: com.reminder.callreminder.phone:id/clReminderRepeat
        // app is crushing when trying to change the repeat setting
    }

    public void setSaveReminder() {
        wait.until(ExpectedConditions.elementToBeClickable(saveReminder)).click();
    }

    public void setUpdateReminder() {
        // com.reminder.callreminder.phone:id/ivDone
        // android:id/button1
        wait.until(ExpectedConditions.elementToBeClickable(saveReminder)).click();
        wait.until(ExpectedConditions.elementToBeClickable(updateConfirmButton)).click();
    }

    public void setUpdateDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(startDateField)).click();

        // Промена на месец (scroll functionality checked)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accessibilityId("Change to next month"))).click();
        } catch (Exception ignored) {
            System.out.println("Already on the correct month.");
        }

        // Селекција на тој датум
        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId(date))).click();
        wait.until(ExpectedConditions.elementToBeClickable(dateConfirmButton)).click();

        // Клик на часовник за понатамошен избор
        wait.until(ExpectedConditions.elementToBeClickable(startTimeField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"8 o'clock\"]")
        )).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"00 minutes\"]")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(timeConfirmButton)).click();
    }

    public void updateReminder(String title, String subtitle, String date) {
        WebElement newTask = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']/android.view.ViewGroup[1]")));
        newTask.click();

        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        setUpdateTitle(title, subtitle);
        setUpdateDate(date);
        setUpdateReminder();
    }
}
