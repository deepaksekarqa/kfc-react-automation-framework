package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import utils.WaitHelper;

public class HomePage extends BaseClass {

    private WaitHelper waitHelper;

    private WaitHelper getWaitHelper() {
        if (waitHelper == null) {
            waitHelper = new WaitHelper(driver, 15);
        }
        return waitHelper;
    }

    // ── Locators ──────────────────────────────────────────────────────────────

    private By locationPopup    = By.xpath(
        "//div[contains(@class,'modal') or contains(@class,'popup') or " +
        "contains(@class,'location')]");

    private By locationCloseBtn = By.xpath(
        "//button[contains(@aria-label,'close') or contains(@aria-label,'Close') or " +
        "contains(text(),'×') or contains(text(),'Skip')]");

    private By kfcLogo          = By.xpath(
        "//img[contains(@alt,'KFC') or contains(@src,'kfc') or contains(@src,'logo')]");

    private By kfcHeader        = By.xpath(
        "//header | //nav[contains(@class,'header') or contains(@class,'nav')]");

    // ── Methods ───────────────────────────────────────────────────────────────

    public void handleLocationPopup() {
        try {
            WaitHelper shortWait = new WaitHelper(driver, 8);
            shortWait.waitForVisibility(locationPopup);

            try {
                shortWait.safeClick(locationCloseBtn);
                shortWait.waitForInvisibility(locationPopup);
                System.out.println("Location popup closed via close button");
            } catch (Exception e) {
                driver.findElement(By.xpath("//body")).click();
                System.out.println("Location popup dismissed by clicking outside");
            }

        } catch (Exception e) {
            System.out.println("No location popup found — continuing");
        }
    }

    public boolean isHomePageLoaded() {
        try {
            getWaitHelper().waitForVisibility(kfcLogo);
            System.out.println("KFC logo visible — homepage loaded");
            return true;
        } catch (Exception e) {
            try {
                getWaitHelper().waitForPresence(kfcHeader);
                System.out.println("KFC header visible — homepage loaded");
                return true;
            } catch (Exception ex) {
                System.out.println("Homepage did not load correctly");
                return false;
            }
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}