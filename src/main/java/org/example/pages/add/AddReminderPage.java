package org.example.pages.add;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.appium.java_client.AppiumBy.accessibilityId;

public class AddReminderPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public AddReminderPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Creation reminder selectors
    public static final By SAVE_REMINDER = By.id("com.reminder.callreminder.phone:id/ivDone");
    public static final By EDIT_BUTTON = By.id("com.reminder.callreminder.phone:id/ivEdit");
    public static final By UPDATE_CONFIRM_BUTTON = By.id("android:id/button1");

    // Call reminder selectors
    public static final By CALLEE = By.id("com.reminder.callreminder.phone:id/etSearch");
    public static final By VIEW_CALL_REMINDER = By.id("com.reminder.callreminder.phone:id/llView");
    public static final By CHANGE_CALLEE = By.id("com.reminder.callreminder.phone:id/tvChange");

    // Title and Subtitle related
    public static final By ADD_TITLE = By.id("com.reminder.callreminder.phone:id/edTitle");
    public static final By ADD_SUBTITLE = By.id("com.reminder.callreminder.phone:id/etItem");
    public static final By SUBTITLE_BUTTON = By.id("com.reminder.callreminder.phone:id/ivAddChak");

    // Date and Time related
    public static final By ADD_DATE = By.id("com.reminder.callreminder.phone:id/swSetTime");
    public static final By START_DATE_FIELD = By.id("com.reminder.callreminder.phone:id/tvStartDate");
    public static final By DATE_CONFIRM_BUTTON = By.id("com.reminder.callreminder.phone:id/confirm_button");
    public static final By START_TIME_FIELD = By.id("com.reminder.callreminder.phone:id/tvStartTime");
    public static final By SELECT_AM = By.id("com.reminder.callreminder.phone:id/material_clock_period_am_button");
    public static final By TIME_CONFIRM_BUTTON = By.id("com.reminder.callreminder.phone:id/material_timepicker_ok_button");


    // Searching selectors
    public static final By TASK_REMINDER_FIRST_ITEM = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']/android.view.ViewGroup[1]");
    public static final By FIRST_SEARCH_RESULT = By.xpath("(//android.widget.LinearLayout[@resource-id='com.reminder.callreminder.phone:id/liner'])[1]");

    // SEARCH CALLEE
    public void searchCallee(String personToCall) {
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(CALLEE));
        titleField.clear();
        titleField.sendKeys(personToCall);

        WebElement resultItem = wait.until(ExpectedConditions.elementToBeClickable(FIRST_SEARCH_RESULT));
        resultItem.click();
    }

    //  ADD / UPDATE TITLE
    public void setTitleFields(String title, String subtitle, boolean updateTitle) {
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_TITLE));
        titleField.clear();
        titleField.sendKeys(title);

        if (!updateTitle) {
            wait.until(ExpectedConditions.elementToBeClickable(SUBTITLE_BUTTON)).click();
        }

        WebElement subtitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_SUBTITLE));
        subtitleField.clear();
        subtitleField.sendKeys(subtitle);
    }

    // ADD / UPDATE DATE
    public void setDateFields(String dateLabel, String hourLabel, String minuteLabel, boolean updateDate) {
        if (!updateDate) {
            wait.until(ExpectedConditions.elementToBeClickable(ADD_DATE)).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(START_DATE_FIELD)).click();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(accessibilityId("Change to next month"))).click();
        } catch (Exception ignored) {
            System.out.println("Already on the correct month.");
        }

        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId(dateLabel))).click();
        wait.until(ExpectedConditions.elementToBeClickable(DATE_CONFIRM_BUTTON)).click();

        wait.until(ExpectedConditions.elementToBeClickable(START_TIME_FIELD)).click();
        wait.until(ExpectedConditions.elementToBeClickable(SELECT_AM)).click();
        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId(hourLabel))).click();
        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId(minuteLabel))).click();
        wait.until(ExpectedConditions.elementToBeClickable(TIME_CONFIRM_BUTTON)).click();
    }

    // SAVE NEW / UPDATED REMINDER
    public void setSaveReminder(Boolean update) {
        wait.until(ExpectedConditions.elementToBeClickable(SAVE_REMINDER)).click();
        if (update) {
            wait.until(ExpectedConditions.elementToBeClickable(UPDATE_CONFIRM_BUTTON)).click();
        }
    }

    // UPDATE TASK REMINDER
    public void updateTaskReminder(String title, String subtitle, String date, String hour, String minute) {
        WebElement newTask = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvTaskReminder']/android.view.ViewGroup[1]")));
        newTask.click();

        wait.until(ExpectedConditions.elementToBeClickable(EDIT_BUTTON)).click();
        setTitleFields(title, subtitle, true);
        setDateFields(date, hour, minute, true);
        setSaveReminder(true);
    }

    // UPDATE CALL REMINDER
    public void updateCallReminder(String title, String subtitle, String date, String hour, String minute, Boolean changeCallee) {
        WebElement newTask = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.reminder.callreminder.phone:id/rvCallReminder']/android.view.ViewGroup[1]")));
        newTask.click();

        wait.until(ExpectedConditions.elementToBeClickable(VIEW_CALL_REMINDER)).click();
        wait.until(ExpectedConditions.elementToBeClickable(EDIT_BUTTON)).click();

        if (changeCallee) {
            wait.until(ExpectedConditions.elementToBeClickable(CHANGE_CALLEE)).click();
            searchCallee("Ana");
            setTitleFields(title, subtitle, false);
            setDateFields(date, hour, minute, false);
            setSaveReminder(false);
        } else {
            setTitleFields(title, subtitle, true);
            setDateFields(date, hour, minute, true);
            setSaveReminder(true);
        }
    }
}

