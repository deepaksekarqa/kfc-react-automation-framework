package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

import java.util.List;

public class CartPage extends BaseClass {

    private WaitHelper waitHelper;

    private WaitHelper getWaitHelper() {
        if (waitHelper == null) {
            waitHelper = new WaitHelper(driver, 15);
        }
        return waitHelper;
    }

    // ── Category Locators ──────────────────────────────────────────────────────

    private By burgersSidebar = By.xpath(
        "//div[normalize-space(text())='BURGERS'] | " +
        "//li[normalize-space(text())='BURGERS'] | " +
        "//a[normalize-space(text())='BURGERS']"
    );

    // ── Menu Item Locators ─────────────────────────────────────────────────────

    private By menuItemCards = By.xpath(
        "//div[contains(@class,'medium-menu-product-card')] | " +
        "//button[contains(@data-testid,'add-to-cart')]"
    );

    private By menuItemName = By.xpath(
        "(//div[contains(@class,'medium-menu-product-card')]//h3)[1] | " +
        "(//*[contains(@data-testid,'menu-card-content')]//h3)[1]"
    );

    // ── Add to Cart Locators ───────────────────────────────────────────────────

    private By firstAddBtn = By.xpath(
        "(//button[contains(@aria-label,'Add to cart') or " +
        "contains(@aria-label,'Add to Cart')])[1] | " +
        "(//button[contains(@data-testid,'add-to-cart')])[1] | " +
        "(//button[contains(@class,'medium-menu-cart-button')])[1]"
    );

    private By customisationModal = By.xpath(
        "//div[contains(@class,'modal') or contains(@class,'customize') or " +
        "contains(@class,'customis') or contains(@class,'addon')]"
    );

    private By confirmAddBtn = By.xpath(
        "//button[contains(text(),'Add to Cart') or contains(text(),'ADD TO CART') or " +
        "contains(text(),'Confirm') or contains(text(),'Done')]"
    );

    private By modalCloseBtn = By.xpath(
        "//div[contains(@class,'modal')]//button[contains(@aria-label,'close') or " +
        "contains(text(),'×') or contains(text(),'Skip')]"
    );

    // ── Cart Locators ──────────────────────────────────────────────────────────

    private By cartCountBadge = By.xpath(
    	    "//*[contains(@class,'cart') and " +
    	    "(number(normalize-space(text())) >= 0) and " +
    	    "string-length(normalize-space(text())) > 0] | " +
    	    "//div[contains(@class,'header')]" +
    	    "//*[number(normalize-space(text())) >= 0 and " +
    	    "string-length(normalize-space(text())) = 1]"
    	);

    private By cartItemsText = By.xpath(
        "//*[contains(text(),'item') or contains(text(),'Item') and " +
        "not(contains(text(),'menu')) and not(contains(text(),'food'))]"
    );

    // ── Methods ───────────────────────────────────────────────────────────────

    public void clickMenuCategory() {
        try {
            getWaitHelper().safeClick(burgersSidebar);
            System.out.println("Clicked BURGERS category from sidebar");
            Thread.sleep(1500);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            try {
                By anyProduct = By.xpath(
                    "(//div[contains(@class,'medium-menu-product-card')])[1] | " +
                    "(//div[contains(@data-testid,'menu-card-content')])[1]"
                );
                getWaitHelper().waitForPresence(anyProduct);
                System.out.println("Items already loaded — skipping category click");
            } catch (Exception ex) {
                System.out.println("Could not click category: " + ex.getMessage());
            }
        }
    }

    public boolean areMenuItemsLoaded() {
        try {
            By productCards = By.xpath(
                "//div[contains(@class,'medium-menu-product-card')] | " +
                "//button[contains(@data-testid,'add-to-cart')]"
            );
            getWaitHelper().waitForPresence(productCards);
            List<WebElement> items = driver.findElements(productCards);
            if (items.size() > 0) {
                System.out.println("Menu items loaded: " + items.size() + " found");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Menu items did not load: " + e.getMessage());
            return false;
        }
    }

    public int getMenuItemCount() {
        try {
            By productCards = By.xpath(
                "//button[contains(@data-testid,'add-to-cart')]"
            );
            getWaitHelper().waitForPresence(productCards);
            int count = driver.findElements(productCards).size();
            System.out.println("Menu item count: " + count);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getFirstMenuItemName() {
        try {
            return getWaitHelper().getText(menuItemName);
        } catch (Exception e) {
            return "Unknown Item";
        }
    }

    public int getCartCountBefore() {
        try {
            String countText = getWaitHelper().getText(cartCountBadge).trim();
            int count = Integer.parseInt(countText);
            System.out.println("Cart count before: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("Cart badge not visible — assuming 0");
            return 0;
        }
    }

    public void clickAddToCart() {
        try {
            // Get ALL add to cart buttons — pick one that's not behind header
            List<WebElement> addBtns = driver.findElements(
                By.xpath("//button[contains(@data-testid,'add-to-cart')]")
            );

            System.out.println("Found " + addBtns.size() + " Add buttons on page");

            WebElement targetBtn = null;

            // Find first button that is below y=150 (below sticky header)
            for (WebElement btn : addBtns) {
                int yPosition = btn.getLocation().getY();
                if (yPosition > 150) {
                    targetBtn = btn;
                    System.out.println("Selected Add button at y=" + yPosition);
                    break;
                }
            }

            if (targetBtn == null) {
                System.out.println("No button below header found — using first button");
                targetBtn = addBtns.get(0);
            }

            // Scroll element to center of viewport — away from header
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                targetBtn
            );
            Thread.sleep(800);

            // Use JavaScript click — bypasses header intercept entirely
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", targetBtn
            );
            System.out.println("Clicked Add button via JS executor");

            handleCustomisationModal();

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Could not click Add button: " + e.getMessage());
        }
    }

    private void handleCustomisationModal() {
        try {
            WaitHelper shortWait = new WaitHelper(driver, 5);
            shortWait.waitForVisibility(customisationModal);
            System.out.println("Customisation modal appeared");
            try {
                shortWait.safeClick(confirmAddBtn);
                System.out.println("Confirmed add inside modal");
            } catch (Exception e) {
                shortWait.safeClick(modalCloseBtn);
                System.out.println("Closed customisation modal");
            }
        } catch (Exception e) {
            System.out.println("No customisation modal — item added directly");
        }
    }

    public int getCartCountAfter() {
        try {
            // Wait 3 seconds for React to update cart state
            Thread.sleep(3000);

            // Try reading cart badge
            List<WebElement> candidates = driver.findElements(
                By.xpath(
                    "//div[contains(@class,'header')]//*[string-length(normalize-space(text()))=1] | " +
                    "//*[contains(@class,'cart')]//*[string-length(normalize-space(text()))<=2 " +
                    "and number(normalize-space(text()))=number(normalize-space(text()))]"
                )
            );

            for (WebElement el : candidates) {
                String text = el.getText().trim();
                if (!text.isEmpty() && text.matches("\\d+")) {
                    int count = Integer.parseInt(text);
                    System.out.println("Cart count after: " + count);
                    return count;
                }
            }

            System.out.println("Cart count candidates checked — trying items text");
            return getCartCountFromItemsText();

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return 0;
        } catch (Exception e) {
            System.out.println("Could not read cart count after: " + e.getMessage());
            return getCartCountFromItemsText();
        }
    }

    private int getCartCountFromItemsText() {
        try {
            String text = getWaitHelper().getText(cartItemsText);
            String number = text.replaceAll("[^0-9]", "").trim();
            int count = number.isEmpty() ? 0 : Integer.parseInt(number);
            System.out.println("Cart count from text: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("Could not read cart count");
            return 0;
        }
    }

    public boolean didCartCountIncrease(int before, int after) {
        boolean increased = after > before;
        System.out.println("Cart check — Before: " + before +
                           " | After: " + after +
                           " | Increased: " + increased);
        return increased;
    }
}