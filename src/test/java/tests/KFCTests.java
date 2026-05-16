package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.MenuPage;

public class KFCTests extends BaseClass {

    HomePage homePage = new HomePage();
    MenuPage menuPage = new MenuPage();
    CartPage cartPage = new CartPage();

    @Test(priority = 1, description = "Verify KFC site loads and branding is visible")
    public void testHomePageLoad() {
        System.out.println("\n===== TEST 1: Home Page Load =====");

        homePage.handleLocationPopup();

        String title = homePage.getPageTitle();
        System.out.println("Page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");

        boolean loaded = homePage.isHomePageLoaded();
        Assert.assertTrue(loaded, "KFC homepage should load with logo/header visible");

        System.out.println("TEST 1 PASSED — KFC homepage loaded successfully");
    }

    @Test(priority = 2, description = "Select Delivery and enter Chennai pincode")
    public void testSelectDelivery() {
        System.out.println("\n===== TEST 2: Select Delivery =====");

        homePage.handleLocationPopup();
        menuPage.selectDelivery();
        menuPage.enterChennaiPincode("600001");
        menuPage.clickProceed();

        boolean locationAccepted = menuPage.isLocationAccepted();
        Assert.assertTrue(locationAccepted,
            "Chennai location (600001) should be accepted for delivery");

        System.out.println("TEST 2 PASSED — Delivery selected, Chennai pincode accepted");
    }

    @Test(priority = 3, description = "Navigate to Menu and verify categories are visible")
    public void testMenuNavigation() {
        System.out.println("\n===== TEST 3: Menu Navigation =====");

        menuPage.navigateToMenu();

        boolean categoriesVisible = menuPage.areCategoriesVisible();
        Assert.assertTrue(categoriesVisible,
            "Menu categories should be visible");

        int categoryCount = menuPage.getCategoryCount();
        Assert.assertTrue(categoryCount >= 2,
            "At least 2 menu categories should be present, found: " + categoryCount);

        System.out.println("TEST 3 PASSED — Menu loaded with " + categoryCount + " categories");
    }

    @Test(priority = 4, description = "Click a menu category and verify items load")
    public void testSelectMenuItem() {
        System.out.println("\n===== TEST 4: Select Menu Item =====");

        cartPage.clickMenuCategory();

        boolean itemsLoaded = cartPage.areMenuItemsLoaded();
        Assert.assertTrue(itemsLoaded, "Menu items should load after clicking a category");

        int itemCount = cartPage.getMenuItemCount();
        Assert.assertTrue(itemCount >= 1,
            "At least 1 menu item should be present, found: " + itemCount);

        String firstName = cartPage.getFirstMenuItemName();
        System.out.println("First item visible: " + firstName);

        System.out.println("TEST 4 PASSED — Category opened with " + itemCount + " items");
    }

    @Test(priority = 5, description = "Add item to cart and verify cart count updates")
    public void testAddToCart() {
        System.out.println("\n===== TEST 5: Add To Cart =====");

        int cartBefore = cartPage.getCartCountBefore();
        System.out.println("Cart count before: " + cartBefore);

        cartPage.clickAddToCart();

        int cartAfter = cartPage.getCartCountAfter();
        System.out.println("Cart count after: " + cartAfter);

        boolean cartUpdated = cartPage.didCartCountIncrease(cartBefore, cartAfter);
        Assert.assertTrue(cartUpdated,
            "Cart count should increase. Before: " + cartBefore + " After: " + cartAfter);

        System.out.println("TEST 5 PASSED — Cart updated from " + cartBefore + " to " + cartAfter);
    }
}