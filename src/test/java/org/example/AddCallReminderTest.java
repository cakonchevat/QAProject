package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.example.pages.tabs.ReminderCallPage;
import org.example.pages.add.ReminderAddCallPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AddCallReminderTest {

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
    public void testAddCallReminder() {
        ReminderCallPage callPage = new ReminderCallPage(driver);
        ReminderAddCallPage addCallPage = new ReminderAddCallPage(driver, wait);

        // Навигација кон call page ако не се наоѓаме таму (од completed tab не може да се постави reminder)
        callPage.navigateToCallTab();
        callPage.tapAddReminderButton();
        callPage.tapCallReminder();

        // Пребарување на човек кон кој сакаме да го поставиме потсетникот за повик
        String person = "Mamich";
        addCallPage.searchCallee(person);

        addCallPage.setAddTitle("Call reminder", "Subtitle for call reminder");
        addCallPage.setAddDate();
        addCallPage.setSaveReminder();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
