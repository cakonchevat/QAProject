package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.add.AddReminderPage;
import org.example.pages.tabs.CommonPageElements;
import org.example.pages.tabs.ReminderCallPage;
import org.example.pages.tabs.ReminderHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AddReminderTest {

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testAddTaskReminder() {
        CommonPageElements commonPageElements = new CommonPageElements(driver);
        AddReminderPage addReminderPage = new AddReminderPage(driver, wait);
        ReminderHomePage homePage = new ReminderHomePage(driver);

        commonPageElements.navigateToTab("home");
        commonPageElements.startReminderCreation("task");

        String title = "Task reminder", date = "Wednesday, June 18", hour = "10 o'clock", minutes = "00 minutes";
        addReminderPage.setTitleFields(title, "Subtitle for task reminder", false);
        addReminderPage.setDateFields(date, hour, minutes, false);
        addReminderPage.setSaveReminder(false);

        homePage.checkExistenceOfTaskReminder(title, date, hour, minutes);
    }

    @Test
    public void testAddCallReminder() {
        AddReminderPage addReminderPage = new AddReminderPage(driver, wait);
        CommonPageElements commonPageElements = new CommonPageElements(driver);
        ReminderCallPage callPage = new ReminderCallPage(driver);

        commonPageElements.navigateToTab("call");
        commonPageElements.startReminderCreation("call");

        String person = "Ana";
        addReminderPage.searchCallee(person);

        String title = "Updated reminder", date = "Monday, June 30", hour = "9 o'clock", minutes = "00 minutes";
        addReminderPage.setTitleFields(title, "Subtitle for call reminder", false);
        addReminderPage.setDateFields(date, hour, minutes, false);
        addReminderPage.setSaveReminder(false);

        callPage.checkExistenceOfCallReminder(person, title, false);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
