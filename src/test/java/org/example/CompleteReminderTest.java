package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.tabs.ReminderCallPage;
import org.example.pages.tabs.ReminderCompletedPage;
import org.example.pages.tabs.ReminderHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CompleteReminderTest {

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
    public void completeActiveReminders() {
        ReminderHomePage homePage = new ReminderHomePage(driver);
        ReminderCallPage callPage = new ReminderCallPage(driver);
        ReminderCompletedPage completedPage = new ReminderCompletedPage(driver);

        homePage.navigateToTaskTab();
        homePage.completeAllReminders();

        callPage.navigateToCallTab();
        callPage.completeSomeReminders();

        completedPage.navigateToCompletedTab();
    }


    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
