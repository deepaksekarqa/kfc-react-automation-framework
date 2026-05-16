package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

import java.util.List;

public class MenuPage extends BaseClass {

    private WaitHelper waitHelper;

    private WaitHelper getWaitHelper() {
        if (waitHelper == null) {
            waitHelper = new WaitHelper(driver, 15);
        }
        return waitHelper;
    }

    // ── Delivery Flow Locators ─────────────────────────────────────────────────

    private By startOrderBtn = By.xpath(
    	    "//*[@id='startOrderItemButton'] | " +
    	    "//*[@data-testid='start-order-button']"
    	);

    private By deliveryBtn = By.xpath(
    	    "//button[@aria-label='order type Delivery'] | " +
    	    "//*[contains(@data-testid,'disposition-order-click-handler-Disposition - Deliver')]"
    	);

    private By pincodeInput = By.xpath(
    	    "//input[@type='text' and (" +
    	    "contains(@placeholder,'pincode') or " +
    	    "contains(@placeholder,'Pincode') or " +
    	    "contains(@placeholder,'Enter') or " +
    	    "contains(@placeholder,'area') or " +
    	    "contains(@placeholder,'location'))]"
    	);

    private By searchLocationBtn = By.xpath(
        "//button[contains(text(),'Search') or contains(text(),'SEARCH') or " +
        "contains(text(),'Find') or contains(text(),'GO') or @type='submit'] | " +
        "//span[contains(text(),'Search')]/ancestor::button"
    );

    private By locationSuggestion = By.xpath(
    	    "//ul//li[1] | " +
    	    "//div[contains(@class,'suggest') or contains(@class,'dropdown') or " +
    	    "contains(@class,'result')]//div[1] | " +
    	    "//div[contains(@class,'option')][1]"
    	);
   
    // Add this new locator at the top with other locators
    private By useThisAddressBtn = By.xpath(
        "//button[normalize-space(text())='Use this Address'] | " +
        "//button[contains(text(),'Use this Address')]"
    );
    
 // Confirm button — appears TWICE:
 // Step 1: after selecting location suggestion (Delivery Address screen)
 // Step 2: after Schedule for your order screen
    private By confirmBtn = By.xpath(
            "//button[normalize-space(text())='Confirm'] | " +
            "//button[normalize-space(text())='CONFIRM']"
        );
       

 // Location confirmed — banner shows "Delivery at: Chennai..."
    private By locationConfirmedText = By.xpath(
        "//*[contains(text(),'Chennai') or contains(text(),'600') or " +
        "contains(text(),'Delivery at') or contains(text(),'Delivering to')]"
    );
   

    private By proceedBtn = By.xpath(
        "//button[contains(text(),'Proceed') or contains(text(),'PROCEED') or " +
        "contains(text(),'Continue') or contains(text(),'Confirm') or " +
        "contains(text(),'Start') or contains(text(),'BEGIN')]"
    );

    // ── Menu Navigation Locators ───────────────────────────────────────────────

    private By menuNavLink = By.xpath(
        "//a[contains(text(),'Menu') or contains(text(),'MENU')] | " +
        "//nav//button[contains(text(),'Menu')] | " +
        "//li[contains(@class,'menu') or contains(@class,'nav')]//a"
    );

    private By menuCategories = By.xpath(
        "//div[contains(@class,'categor')] | " +
        "//ul[contains(@class,'categor')]//li | " +
        "//div[contains(@class,'tab') and contains(@role,'tab')] | " +
        "//button[contains(@class,'categor')]"
    );

    private By burgersCategory = By.xpath(
        "//*[contains(text(),'Burger') or contains(text(),'BURGER') or " +
        "contains(text(),'Burgers') or contains(text(),'BURGERS')]"
    );

    private By chickenCategory = By.xpath(
        "//*[contains(text(),'Chicken') or contains(text(),'CHICKEN') or " +
        "contains(text(),'Fried Chicken')]"
    );
    




    // ── Methods ───────────────────────────────────────────────────────────────

    public void selectDelivery() {
        try {
            getWaitHelper().safeClick(startOrderBtn);
            System.out.println("Clicked Start Order button");
            getWaitHelper().safeClick(deliveryBtn);
            System.out.println("Clicked Delivery inside modal");
        } catch (Exception e) {
            System.out.println("Could not complete delivery selection: " + e.getMessage());
        }
    }

    public void enterChennaiPincode(String pincode) {
        try {
            // Step 1 — Type pincode
            getWaitHelper().waitForVisibility(pincodeInput);
            getWaitHelper().safeType(pincodeInput, pincode);
            System.out.println("Entered pincode: " + pincode);

            // Wait for suggestion dropdown
            Thread.sleep(1500);

            // Step 2 — Click first suggestion
            getWaitHelper().safeClick(locationSuggestion);
            System.out.println("Selected location suggestion");

            // Step 3 — Click Confirm on Delivery Address screen
            Thread.sleep(800);
            By confirmDeliveryBtn = By.xpath(
                "//*[@data-testid='btn-confirm']"
            );
            getWaitHelper().safeClick(confirmDeliveryBtn);
            System.out.println("Clicked Confirm on Delivery Address screen");

            // Step 4 — Click "Use this Address"
            Thread.sleep(800);
            By useThisAddressBtn = By.xpath(
                "//*[@data-testid='use-this-address']"
            );
            getWaitHelper().safeClick(useThisAddressBtn);
            System.out.println("Clicked Use this Address");

            // Step 5 — Wait for Change Your Order modal
            Thread.sleep(1000);
            By scheduleConfirmBtn = By.xpath(
                "//*[@data-testid='confirm-button-handler']"
            );
            getWaitHelper().waitForVisibility(scheduleConfirmBtn);

            // JS click — this button had overlay issues
            WebElement confirmSchedule = driver.findElement(scheduleConfirmBtn);
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", confirmSchedule
            );
            System.out.println("Clicked Confirm on Change Your Order screen");

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Could not complete pincode flow: " + e.getMessage());
        }
    }

    public boolean isLocationAccepted() {
        try {
            getWaitHelper().waitForVisibility(locationConfirmedText);
            System.out.println("Location accepted — Chennai delivery banner visible");
            return true;
        } catch (Exception e) {
            System.out.println("Location confirmation text not found");
            return false;
        }
    }

    public void clickProceed() {
        // No longer needed — Confirm buttons handled inside enterChennaiPincode
        System.out.println("clickProceed skipped — handled in enterChennaiPincode");
    }

    public void navigateToMenu() {
        try {
            getWaitHelper().safeClick(menuNavLink);
            System.out.println("Navigated to Menu");
        } catch (Exception e) {
            driver.navigate().to("https://online.kfc.co.in/menu");
            System.out.println("Navigated to Menu via direct URL");
        }
    }

    public boolean areCategoriesVisible() {
        try {
            getWaitHelper().waitForPresence(menuCategories);
            List<WebElement> cats = driver.findElements(menuCategories);
            if (cats.size() > 0) {
                System.out.println("Found " + cats.size() + " menu categories");
                return true;
            }
        } catch (Exception e) {
            // fallthrough
        }
        try {
            getWaitHelper().waitForVisibility(burgersCategory);
            System.out.println("Burgers category visible");
            return true;
        } catch (Exception e) {
            try {
                getWaitHelper().waitForVisibility(chickenCategory);
                System.out.println("Chicken category visible");
                return true;
            } catch (Exception ex) {
                System.out.println("No menu categories found");
                return false;
            }
        }
    }

    public int getCategoryCount() {
        try {
            getWaitHelper().waitForPresence(menuCategories);
            int count = driver.findElements(menuCategories).size();
            System.out.println("Category count: " + count);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }
}