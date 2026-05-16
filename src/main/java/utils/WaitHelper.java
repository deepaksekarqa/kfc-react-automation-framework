package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private WebDriverWait wait;

    public WaitHelper(WebDriver driver, int timeoutSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    // Wait until element is VISIBLE on screen
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until element is CLICKABLE
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait until element is PRESENT in DOM (may not be visible)
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait until element TEXT contains expected string
    public boolean waitForTextToContain(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // Wait until element DISAPPEARS (useful for popups closing)
    public boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // Click only when element is clickable — safe click
    public void safeClick(By locator) {
        waitForClickable(locator).click();
    }

    // Type into a field only when visible — safe sendKeys
    public void safeType(By locator, String text) {
        waitForVisibility(locator).sendKeys(text);
    }

    // Get text from element only when visible
    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }
}