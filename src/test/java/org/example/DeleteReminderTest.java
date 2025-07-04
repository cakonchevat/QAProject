package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.add.AddReminderPage;
import org.example.pages.tabs.CommonPageElements;
import org.example.pages.tabs.ReminderCallPage;
import org.example.pages.tabs.ReminderHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DeleteReminderTest {

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
    public void deleteTaskReminders() throws InterruptedException {
        ReminderHomePage homePage = new ReminderHomePage(driver);
        CommonPageElements commonPageElements = new CommonPageElements(driver);

        commonPageElements.navigateToTab("home");
        commonPageElements.longPressOnReminderByIndex(1, "task");
        homePage.deleteAllReminders();
    }

    @Test
    public void deleteCallReminder() throws InterruptedException {
        AddReminderPage addReminderPage = new AddReminderPage(driver, wait);
        ReminderCallPage callPage = new ReminderCallPage(driver);
        CommonPageElements commonPageElements = new CommonPageElements(driver);

        commonPageElements.navigateToTab("call");
        commonPageElements.startReminderCreation("call");

        String callee = "Dragana", title = "Call colleague";
        addReminderPage.searchCallee(callee);
        addReminderPage.setTitleFields(title, "Delete this reminder", false);
        addReminderPage.setDateFields("Wednesday, June 18", "12 o'clock", "00 minutes", false);
        addReminderPage.setSaveReminder(false);

        callPage.checkExistenceOfCallReminder(callee, title, true);
    }


    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
