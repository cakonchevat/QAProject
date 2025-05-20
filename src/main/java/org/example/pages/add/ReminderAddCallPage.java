package org.example.pages.add;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.appium.java_client.AppiumBy.accessibilityId;

public class ReminderAddCallPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    private By callee = By.id("com.reminder.callreminder.phone:id/etSearch");
    private final By addTitle = By.id("com.reminder.callreminder.phone:id/edTitle");
    private final By addSubtitle = By.id("com.reminder.callreminder.phone:id/etItem");
    private final By addDate = By.id("com.reminder.callreminder.phone:id/swSetTime");
    private final By saveReminder = By.id("com.reminder.callreminder.phone:id/ivDone");

    // Датум-ориентирани
    private final By startDateField = By.id("com.reminder.callreminder.phone:id/tvStartDate");
    private final By dateConfirmButton = By.id("com.reminder.callreminder.phone:id/confirm_button");

    // Време-ориентирани
    private final By startTimeField = By.id("com.reminder.callreminder.phone:id/tvStartTime");
    private final By timeConfirmButton = By.id("com.reminder.callreminder.phone:id/material_timepicker_ok_button");

    public ReminderAddCallPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    public void searchCallee(String personToCall) {
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(callee));
        titleField.clear();
        titleField.sendKeys(personToCall);

        By firstSearchResult = By.xpath("(//android.widget.LinearLayout[@resource-id='com.reminder.callreminder.phone:id/liner'])[1]");
        WebElement resultItem = wait.until(ExpectedConditions.elementToBeClickable(firstSearchResult));
        resultItem.click();
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

    public void setAddDate() {
        // Клик на копче за внес на датум
        wait.until(ExpectedConditions.elementToBeClickable(addDate)).click();

        // Клик на датум за понатамошен избор
        wait.until(ExpectedConditions.elementToBeClickable(startDateField)).click();

        // Промена на месец (scroll functionality checked)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(accessibilityId("Change to next month"))).click();
        } catch (Exception ignored) {
            System.out.println("Already on the correct month.");
        }

        // Селекција на тој датум
        wait.until(ExpectedConditions.elementToBeClickable(accessibilityId("Wednesday, June 18"))).click();

        // Клик на OK копче за зачувување на соодветниот датум
        wait.until(ExpectedConditions.elementToBeClickable(dateConfirmButton)).click();

        // Клик на часовник за понатамошен избор
        wait.until(ExpectedConditions.elementToBeClickable(startTimeField)).click();

        // Избор на час (статички поставен на 8)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"8 o'clock\"]")
        )).click();

        // Избор на минути (статички поставен на 00)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@content-desc=\"00 minutes\"]")
        )).click();

        // Клик на OK копче за зачувување на соодветното време
        wait.until(ExpectedConditions.elementToBeClickable(timeConfirmButton)).click();
    }

    // апликацијата паѓа при селекција на repetition на аларм, затоа не е испитан тој аспект

    public void setSaveReminder() {
        wait.until(ExpectedConditions.elementToBeClickable(saveReminder)).click();
    }

}
