# 🍗 KFC India – Online Food Ordering Automation Testing

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green?style=flat-square&logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.x-blue?style=flat-square&logo=apachemaven)
![Status](https://img.shields.io/badge/Tests-5%2F5%20Passed-brightgreen?style=flat-square)

---

## 📌 Project Overview

End-to-end **Selenium automation framework** for the KFC India online food ordering React SPA.  
This project introduces **Explicit Waits** as the primary wait strategy and a **WaitHelper utility class** to handle React's dynamic element rendering — replacing `Thread.sleep` across all test interactions.

**Application Under Test:** https://online.kfc.co.in/

---

## 🎯 Test Cases Covered

| # | Test Method | Description | Result |
|---|------------|-------------|--------|
| 1 | testHomePageLoad | Verify KFC homepage loads | ✅ PASSED |
| 2 | testSelectDelivery | Select delivery option | ✅ PASSED |
| 3 | testMenuNavigation | Navigate to menu page | ✅ PASSED |
| 4 | testSelectMenuItem | Select BURGERS category | ✅ PASSED |
| 5 | testAddToCart | Add item to cart and verify count | ✅ PASSED |

**Total: 5/5 Passed — 0 Failures — 0 Skips**

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Programming language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| TestNG | 7.11.0 | Test framework |
| Maven | 3.x | Build tool and dependency management |
| WebDriverManager | 5.7.0 | Automatic ChromeDriver management |
| Commons IO | 2.15.1 | Utility support |

---

## 📁 Project Structure

```
KFCAutomation/
├── src/
│   ├── main/java/
│   │   ├── base/
│   │   │   └── BaseClass.java          ← static WebDriver + WebDriverWait
│   │   ├── pages/
│   │   │   ├── HomePage.java           ← Homepage and location popup
│   │   │   ├── MenuPage.java           ← Delivery flow + menu navigation
│   │   │   └── CartPage.java           ← Add to cart + cart count verification
│   │   └── utils/
│   │       └── WaitHelper.java         ← Explicit Wait utility class
│   └── test/java/
│       └── tests/
│           └── KFCTests.java           ← 5 @Test methods
├── pom.xml
└── testng.xml
```

---

## ⚙️ Prerequisites

- Java 21 or higher installed
- Maven 3.x installed
- Google Chrome browser installed

---

## 🚀 How to Run

### Option 1 — Run from Eclipse IDE

1. Clone this repository
   ```bash
   git clone https://github.com/deepaksekarqa/kfc-react-automation-framework.git
   ```
2. Open Eclipse → **File → Import → Maven → Existing Maven Project**
3. Right click `testng.xml` → **Run As → TestNG Suite**

### Option 2 — Run from Command Line (Maven)

```bash
git clone https://github.com/deepaksekarqa/kfc-react-automation-framework.git
cd kfc-react-automation-framework
mvn test
```

---

## 📊 Test Results

```
===============================================
KFC India Test Suite
Total tests run: 5, Passes: 5, Failures: 0, Skips: 0
===============================================
```

---

## 🔑 Key Implementation Highlights

- **WaitHelper utility class** — `safeClick()`, `safeType()`, `waitForVisibility()`, `waitForClickable()`, `jsClick()`, `getText()` — centralises all wait logic
- **Lazy initializer pattern** — `getWaitHelper()` creates WaitHelper only when first called — avoids NullPointerException when static driver is not yet set at field init time
- **JavascriptExecutor click** — bypasses sticky header intercepts and overlay blocks on the KFC SPA
- **7-step delivery flow** — Start Order → Delivery → Pincode → Confirm → Use this Address → Schedule → Confirm — all handled with `data-testid` locators
- **utils in src/main/java** — Maven scoping rule — WaitHelper must NOT be in `src/test/java`

---

## ⚠️ Site Challenges Handled

| Challenge | Solution Applied |
|-----------|----------------|
| React SPA dynamic elements | Explicit Waits (WebDriverWait) throughout |
| Location popup on load | try/catch with short wait |
| 7-step modal delivery flow | Step-by-step with data-testid locators |
| Sticky header click intercept | y > 150 scan + JavascriptExecutor click |
| Dynamic class names | contains() XPath + data-testid patterns |

---

## ⚠️ Important Notes

- No personal credentials or location data is hardcoded — pincode 600001 is a public Chennai postal code
- Thread.sleep used only for React debounce (1.5s after typing) — not as general wait strategy
- SLF4J and CDP warnings in console are harmless

---

## 👨‍💻 Author

**Deepak S** — QA Engineer (Automation & Manual Testing)  
📍 Chennai, India  
🔗 [LinkedIn](https://www.linkedin.com/in/deepaksekar7/) | [GitHub](https://github.com/deepaksekarqa)

---

