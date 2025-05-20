package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.add.ReminderAddCallPage;
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
    public void deleteReminders() throws InterruptedException {
        ReminderHomePage homePage = new ReminderHomePage(driver);
        ReminderCallPage callPage = new ReminderCallPage(driver);
        ReminderAddCallPage addCallPage = new ReminderAddCallPage(driver, wait);

        homePage.navigateToTaskTab();
        homePage.longPressOnTaskByIndex(1);
        homePage.deleteAllReminders();

        callPage.navigateToCallTab();

        // креирање на потсетник кој ќе се избрише
        callPage.tapAddReminderButton();
        callPage.tapCallReminder();
        String callee = "Miki";
        addCallPage.searchCallee(callee);
        String title = "Call boyfriend";
        addCallPage.setAddTitle(title, "Subtitle for delete_made_reminder");
        addCallPage.setAddDate();
        addCallPage.setSaveReminder();


        callPage.longPressOnTaskByIndex(1);
        callPage.deleteReminder(callee, title);
    }


    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
