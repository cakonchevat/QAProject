package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.add.AddReminderPage;
import org.example.pages.tabs.CommonPageElements;
import org.example.pages.tabs.ReminderHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class UpdateReminderTest {

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
    public void updateTaskReminder() throws InterruptedException {
        ReminderHomePage homePage = new ReminderHomePage(driver);
        AddReminderPage addReminderPage = new AddReminderPage(driver, wait);
        CommonPageElements commonPageElements = new CommonPageElements(driver);

        commonPageElements.navigateToTab("home");
        commonPageElements.startReminderCreation("task");

        addReminderPage.setTitleFields("Update this reminder", "Update status: false", false);
        addReminderPage.setDateFields("Monday, June 30", "8 o'clock", "45 minutes", false);
        addReminderPage.setSaveReminder(false);

        String update_title = "Updated reminder", update_sub = "Update status: true",
        update_date = "Saturday, June 28", update_hour = "8 o'clock", update_minutes = "00 minutes";
        addReminderPage.updateTaskReminder(update_title, update_sub, update_date, update_hour, update_minutes);
        homePage.checkExistenceOfTaskReminder(update_title, update_date, update_hour, update_minutes);
    }

    @Test
    public void updateCallReminder() throws InterruptedException {
        AddReminderPage addReminderPage = new AddReminderPage(driver, wait);
        CommonPageElements commonPageElements = new CommonPageElements(driver);

        commonPageElements.navigateToTab("call");
        commonPageElements.startReminderCreation("call");

        addReminderPage.searchCallee("Miki");
        addReminderPage.setTitleFields("Update this reminder", "Update status: false", false);
        addReminderPage.setDateFields("Monday, June 30", "8 o'clock", "45 minutes", false);
        addReminderPage.setSaveReminder(false);

        addReminderPage.updateCallReminder("Updated reminder", "Update status: true", "Saturday, June 28", "8 o'clock", "00 minutes", false);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
