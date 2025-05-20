package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.add.ReminderAddTaskPage;
import org.example.pages.tabs.ReminderHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AddTaskReminderTest {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("11")
                .setDeviceName("TCL 20L")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.reminder.callreminder.phone")
                .setAppActivity(".ui.LaunchMainActivity")
                .setNoReset(true)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAddReminder() {
        ReminderHomePage homePage = new ReminderHomePage(driver);
        ReminderAddTaskPage addTaskPage = new ReminderAddTaskPage(driver, wait);

        homePage.navigateToTaskTab();
        homePage.tapAddReminderButton();
        homePage.tapTaskReminder();

        addTaskPage.setAddTitle("Task reminder", "Subtitle for task reminder");
        addTaskPage.setAddDate("Wednesday, June 18");
        addTaskPage.setSaveReminder();

        homePage.longPressOnTaskByIndex(1);

    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
