
// DAY 46
/*
 * 1️⃣ What Is Playwright and How Is It Different from Selenium or Cypress?
 * 📌 Question: Why is Playwright gaining popularity, and how does it differ
 * from traditional test frameworks?
 *
 * 
 * | Reason | Explanation |
 * | -------------------------------- |
 * ------------------------------------------------------------------------ |
 * | ⚡ **Faster & More Reliable** | Auto-waits for elements to be ready,
 * reducing flaky tests |
 * | 🌐 **Cross-browser Support** | Supports Chromium, Firefox, and WebKit **out
 * of the box** |
 * | 👥 **Multi-tab/Context Testing** | Easily test multiple browser tabs,
 * sessions, or users in parallel |
 * | 🔧 **Powerful Features** | Built-in support for file uploads, downloads,
 * network mocking, etc. |
 * | 🧪 **Headless & Headed Modes** | Supports both for local dev and CI
 * pipelines |
 * | 📦 **All-in-one Tooling** | Comes bundled with test runner, assertions, and
 * tracing — no extra setup |
 * 
 * 
 * Here’s a **Playwright vs Selenium comparison as a clear list** for quick
 * reference:
 * 
 * ---
 * 
 * ### ✅ **Playwright vs Selenium – Feature-by-Feature Comparison (List)**
 * 
 * 1. **Language Support**
 * 
 * ✅ **Playwright:** JavaScript, TypeScript, Python, Java, .NET
 * ✅ **Selenium:** All major languages (Java, Python, C#, JS, Ruby)
 * 
 * 2. **Browser Support**
 * 
 * ✅ **Playwright:** Chromium, Firefox, WebKit (includes Safari)
 * ✅ **Selenium:** All browsers via WebDriver (including legacy ones)
 * 
 * 3. **Installation & Setup**
 * 
 * ✅ **Playwright:** Simple, one-package install (`playwright`)
 * ❌ **Selenium:** Needs separate drivers (ChromeDriver, GeckoDriver, etc.)
 * 
 * 4. **Auto-Waiting**
 * 
 * ✅ **Playwright:** Built-in auto-wait for elements and events
 * ❌ **Selenium:** Manual waits often required (explicit/implicit)
 * 
 * 5. **Multiple Tabs/Contexts**
 * 
 * ✅ **Playwright:** Supports multiple tabs/sessions natively
 * ⚠️ **Selenium:** Possible but requires more complex handling
 * 
 * 6. **Headless Mode**
 * 
 * ✅ **Both:** Supported
 * 
 * 7. **Test Runner**
 * 
 * ✅ **Playwright:** Built-in test runner with assertions and reporters
 * ❌ **Selenium:** Needs external test frameworks (JUnit, TestNG, etc.)
 * 
 * 8. **Speed & Reliability**
 * 
 * ✅ **Playwright:** Fast with less flaky tests due to smart waits
 * ⚠️ **Selenium:** Slower, more prone to flakiness without tuning
 * 
 * 9. **Network Interception / Mocking**
 * 
 * ✅ **Playwright:** Built-in and easy to use
 * ❌ **Selenium:** Requires external tools or proxy setup
 * 
 * 10. **Mobile Emulation**
 * 
 * ✅ **Playwright:** Built-in emulation for devices and geolocation
 * ⚠️ **Selenium:** Limited via Chrome DevTools
 * 
 * 11. **Parallel Test Execution**
 * 
 * ✅ **Playwright:** Supported out of the box
 * ❌ **Selenium:** Needs grid or external solutions (Selenium Grid, Selenoid)
 * 
 * 12. **Cross-Platform Support**
 * 
 * ✅ **Both:** Yes (Windows, macOS, Linux)
 * 
 * 13. **Community & Maturity**
 * 
 * ⚠️ **Playwright:** Newer (since \~2020), growing fast
 * ✅ **Selenium:** Established, large ecosystem
 * 
 * 14. **Use in Legacy Systems**
 * 
 * ⚠️ **Playwright:** Less suited due to modern browser constraints
 * ✅ **Selenium:** Better for older apps and wider browser support
 * 
 * 15. **Learning Curve**
 * 
 * ✅ **Playwright:** Modern and cleaner API, easier to start
 * ⚠️ **Selenium:** Steeper, especially with waits and setups
 */

/*
 * 2️⃣ How Do You Launch a Browser and Open a Page Using Playwright?
 * 📌 Question: What is the basic code to launch a browser and open a website
 * using Playwright in JavaScript/TypeScript?
 */
const { chromium } = require('playwright');
(async () => {
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  const page = await context.newPage();
  await page.goto('https://playwright.dev');
  console.log(await page.title());
  await browser.close();
})();



/*
   * 3️⃣ What Is a Locator in Playwright and How Is It Different from Traditional
   * Selectors?
   * 📌 Question: What are locators in Playwright, and how do they improve test
   * reliability?
   * 
   * In Playwright, a locator is an abstraction for selecting elements on a web
   * page.
   * It allows you to interact with elements more reliably and intelligently than
   * using raw CSS/XPath selectors.
   * 
   * The locator waits for the element to:
      Appear in the DOM
      Be visible
      Be actionable (e.g. not disabled)
   */
(async () => {
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  const page = await context.newPage();
  await page.goto('https://playwright.dev');
  await page.click("button#submit") // Not retry act immeditely
  await page.locator("button#submit").click() // Retry - auto wait 
})();


/*
 * 4️⃣ Write a Simple Test to Verify Page Title Using Playwright Test Runner
📌 Question: How do you verify the page title using Playwright’s built-in test runner?
 */

import { test, expect } from '@playwright/test';

test("Verify Page Title", async ({ page }) => {
  await page.goto('https://playwright.dev');
  await expect(page).toHaveTitle("Fast and reliable end-to-end testing for modern web apps | Playwright");
})

//DAY 47
/*
  * 1️⃣ What Are the Different Types of Selectors Supported by Playwright?
  * 📌 Question: What types of element selectors can you use in Playwright and
  * when should you prefer one over the other?
  * page.getByRole() to locate by explicit and implicit accessibility attributes.
  * page.getByText() to locate by text content.
  * page.getByLabel() to locate a form control by associated label's text.
  * page.getByPlaceholder() to locate an input by placeholder.
  * page.getByAltText() to locate an element, usually image, by its text
  * alternative.
  * page.getByTitle() to locate an element by its title attribute.
  * page.getByTestId() to locate an element based on its data-testid attribute
  * (other attributes can be configured).
  * 
  * Playwright supports a variety of selectors — but your goal should be
  * stability and readability.
  * Preferred Hierarchy (most stable to least):
  * getByTestId / getByRole
  * getByLabel, getByText
  * CSS Selectors
  * XPath (last resort)
  */

/*
 * 2️⃣ How Do You Assert That Text Is Visible on a Page in Playwright?
📌 Question: How can you validate if a specific text is present and visible using Playwright?
 */
test("Verify Page Title", async ({ page }) => {
  await page.goto('https://playwright.dev');
  await expect(page.locator("button#submit")).toHaveText("Please Submit")
})


/*
 * 3️⃣ What Is a Browser Context in Playwright and Why Is It Useful?
 * 📌 Question: Explain the concept of browser context and how it helps in
 * automation.
 * 
 * ✅ Answer:
 * A browser context is an isolated session, like a separate incognito window.
 * Multiple contexts can share a browser instance but maintain independent
 * Cookies
 * Cache
 * Local/session storage
 * Authentication state
 * 
 * Useful for:
 * ✔️ Test Isolation: Each test runs in its own session with no shared cookies,
 * storage, or auth.
 * ✔️ Performance: Faster than launching a full new browser instance for every
 * test.
 * ✔️ Parallelism: Multiple contexts can run in parallel without interfering.
 */

/*
* 4️⃣ How Do You Run a Playwright Test in Multiple Browsers (Chromium, Firefox,
* WebKit)?
* 
* You can run tests in multiple browsers using the Playwright Test Runner by
* simply configuring it in the playwright.config.js (or .ts) file.
* // playwright.config.js
* const { defineConfig, devices } = require('@playwright/test');
* module.exports = defineConfig({
* projects: [
* {
* name: 'Chromium',
* use: { browserName: 'chromium' },
* },
* {
* name: 'Firefox',
* use: { browserName: 'firefox' },
* },
* {
* name: 'WebKit',
* use: { browserName: 'webkit' },
* },
* ],
* });
* 
*/

/*
* 5️⃣ How to Assert Element Visibility in Playwright?
* 📌 Question: How can you check if an element is visible or hidden on a page?
* 
* Playwright provides powerful built-in assertions to check if an element is:
* ✅ Visible
* await expect(page.locator('text=Login')).toBeVisible();
* 
* ❌ Hidden
* ❌ Detached (removed from the DOM)
* await expect(page.locator('#popup')).toBeHidden();
* This checks if the element is:
* display: none
* visibility: hidden
* Outside of viewport
* Or not in the DOM at all
* 
* If you want to confirm the element exists but is not visible:
* await expect(page.locator('#popup')).toBeHidden();
* 
*/


// DAY 48
/*
 * 1️⃣ How Do You Type Into an Input Field in Playwright?
 * 📌 Question: How do you enter text into an input box using Playwright?
 * 
 * ✅ 1. page.fill() – Most Common & Recommended
 * Clears the field before typing
 * Fast and reliable
 * Best for most form fields
 * 
 * ✅ 2. page.locator().fill()
 */

await page.fill('#username', 'john.doe@example.com');
await page.locator('input[name="email"]').fill('john.doe@example.com');



/*
 * 2️⃣ How to Select a Value from a Dropdown in Playwright?
 * 📌 Question: What’s the best way to select an option from a <select>
 * dropdown?
 * 
 * | Selector Type | Code Example |
 * | ------------- | ----------------------------------- |
 * | By value | `selectOption('US')` |
 * | By label | `selectOption({ label: 'Canada' })` |
 * | By index | `selectOption({ index: 1 })` |
 * 
 * await dropdown.selectOption({ value: 'US' });
 * await expect(page.locator('#country')).toHaveValue('US');
 */
await page.locator("select#country").selectOption("India")
await page.locator("select#country").selectOption({ label: "IN" })
await page.locator("select#country").selectOption({ value: "india" })
await page.locator("select#country").selectOption({ index: 1 })
await page.locator("select#colors").selectOption([{ index: 1 }, { value: "red" }, "India"])


/*
 * 3️⃣ How Do You Upload a File Using Playwright?
📌 Question: How do you simulate file upload with Playwright?
 */
await page.locator("input[type='file']").setInputFiles("File/testfile.pdf");

/*
4️⃣ How to Handle Checkboxes in Playwright?
📌 Question: How do you verify and toggle a checkbox?
*/
const checkbox = page.locator('hashtag#acceptTerms');
await expect(checkbox).not.toBeChecked();
if (!await checkbox.isChecked()) {
  checkbox.check()
}
await expect(checkbox).toBeChecked();

/*
 * 5️⃣ How to Configure Retries for a Failing Test in Playwright?
 * 📌 Question: Can you retry a test automatically if it fails?
 * 
 * ✅ 1. Set Global Retries in playwright.config.js
 * // playwright.config.js
 * const { defineConfig } = require('@playwright/test');
 * 
 * module.exports = defineConfig({
 * retries: 2, // Retry failed tests up to 2 times
 * });
 * 
 * ✅ 2. Set Retries for a Specific Test File
 * test.describe.configure({ retries: 2 });
 * 
 * 3. Retry an Individual Test Only
 * test.retry(1)('retry just this test', async ({ page }) => {
  // test steps
});

🧪 Optional: Check If Test is a Retry
You can run different logic if the test is retrying:

test('check retry attempt', async ({ page }, testInfo) => {
  if (testInfo.retry) {
    console.log(`Retry attempt #${testInfo.retry}`);
  }
});
 */


// DAY 49

/*
 * 1️⃣ How Do You Perform Keyboard Actions in Playwright?
 * 📌 Question: How do you simulate pressing Enter or typing with modifiers like
 * Shift in Playwright?
 * 
 * | Action | Command |
 * | ----------------------- | ------------------------------------ |
 * | Type text | `page.keyboard.type('hello')` |
 * | Press a key | `page.keyboard.press('Enter')` |
 * | Hold modifier | `keyboard.down('Shift')` + `press()` |
 * | Release modifier | `keyboard.up('Shift')` |
 * | Key combo (e.g. Ctrl+A) | `keyboard.press('Control+A')` |
 * | Focus + type | `page.click()` + `keyboard.type()` |
 * 
 */
await page.keyboard.press('Enter');
await page.keyboard.press('Tab');
await page.keyboard.press('ArrowDown');

await page.keyboard.down('Shift');
await page.keyboard.press('KeyA'); // results in 'A' (uppercase)
await page.keyboard.up('Shift');

await page.click('#search');               // Focus the input field
await page.keyboard.type('Playwright');    // Type text
await page.keyboard.press('Enter');        // Submit form

await page.keyboard.press('Control+A'); // Select all
await page.keyboard.press('Control+C'); // Copy


/*
 * 2️⃣ How Do You Simulate Mouse Hover or Drag-Drop in Playwright?
📌 Question: How can you perform hover or drag-and-drop operations?
 */
await page.locator("#selector").hover()
await page.dragAndDrop("#source", "#target")

/*
 * 3️⃣ How Do You Handle JavaScript Alerts/Popups in Playwright?
📌 Question: How do you accept or dismiss browser alerts?
 */
page.on("dialog", async dialog => {
  dialog.type() // Return Alert type
  dialog.message() // Text in Alert
  dialog.accept() // Click OK
  dialog.dismiss() // clican Cancel
  dialog.accept("Promtp value to be typed") // Click ok with input to prompt
});


/*
 * 4️⃣ How to Capture a Screenshot in Playwright?
📌 Question: How do you take a screenshot of a full page or a specific element?
 */
// View port
await page.screenshot({ path: 'screenshot-viewport.png' });
// Full page
await page.screenshot({ path: 'screenshot-full.png', fullPage: true });
// Specific element
await page.locator('#profile-picture').screenshot({ path: 'profile.png' });
// On Test Failure Automatic
// playwright.config.js
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  use: {
    screenshot: 'only-on-failure', // or 'on' or 'off'
  },
});


/*
 * 5️⃣ What Are test.skip, test.only, and test.describe in Playwright?
📌 Question: How do you organize and control test execution using annotations?

These are test modifiers and groupings that give you fine-grained control over which tests run and how they’re structured.
✅ 1. test.skip — 💤 Skip a Test (Temporarily Disable It)
✅ 2. test.only — 🔍 Run Only This Test (Focus Mode)
✅ 3. test.describe — 📦 Group Related Tests
 */
test.describe('User Profile', () => {
  test('should load profile', async ({ page }) => {
    // test code
  });

  test.skip('should update profile picture', async ({ page }) => {
    // skipped test
  });

  test.only('should edit username', async ({ page }) => {
    // only this will run
  });

  test.only(condition == (1 == 0), 'should edit username', async ({ page }) => {
    // only this will run
  });

});


// DAY 50

/*
 * 1️⃣ What Are the Different Wait Mechanisms in Playwright?
 * 📌 Question: How does Playwright handle waiting for elements, and when should
 * you use explicit waits?
 * 
 * Playwright has smart auto-waiting built-in — but it also gives you tools for
 * explicit and custom waits when needed.
 * 
 * ✅ 1. Auto-Waiting (Built-In – Most Common)
 * Playwright automatically waits for:
 * Elements to appear in the DOM
 * Elements to become visible and enabled
 * Navigation to complete (page.goto, click, submit, etc.)
 * 
 * ✅ 2. Explicit Waits Using locator.waitFor()
 * 
 * ✅ 3. Assertions with Auto-Waiting and customize with timeout
 * 
 * ✅ 4. Manual Waits (Not Recommended, But Sometimes Useful)
 * 
 * ✅ 5. Wait for Navigation or Events
 * 
 */
// 2
await page.locator('#success-message').waitFor();
await page.locator('#loader').waitFor({ timeout: 5000 });

// 3
await expect(page.locator('text=Success')).toBeVisible({ timeout: 7000 });

// 4
await Promise.all([
  page.waitForURL("*/target.html"),
  page.click('a#go-to-dashboard'),
]);
await page.waitForLoadState('networkidle');

// 5 - Avoid
await page.waitForTimeout(2000);


/*
* 2️⃣ How Do You Intercept and Modify Network Requests in Playwright?
* 📌 Question: How do you intercept an API call and return a mocked response?
* 
* You use page.route() to intercept a request, and then either:
* Modify, mock, or block the response
* Let it pass through
* 
* | Task | Use This |
* | ------------------------ | --------------------------- |
* | Mock API response | `route.fulfill()` |
* | Modify request (headers) | `route.continue()` |
* | Abort request (block it) | `route.abort()` |
* | Match by pattern | `'**'/api/**'`, RegExp, etc. |
*/
// ✅ 1. Basic Example: Intercept and Mock a Response
// This intercepts all requests to /api/user and returns a fake user object instead of hitting the server.
// Mock
await page.route('**/api/user', async route => {
  await route.fulfill({
    status: 200,
    contentType: 'application/json',
    body: JSON.stringify({ name: 'Mocked User', age: 30 }),
  });
});

await page.goto('https://your-app.com');
await page.goto('https://app.com/api/user');

// ✅ 2. Modify Request or Add Headers
await page.route('**/api/**', async route => {
  const request = route.request();
  const headers = {
    ...request.headers(),
    Authorization: 'Bearer mocked-token'
  };
  route.continue({ headers });
});

// ✅ 5. Use route.abort() to Block Requests (e.g. ads or images)
await page.route('**/*.png', route => route.abort());

/*
 * 3️⃣ How to Record Video of Playwright Test Execution?
 * 📌 Question: How do you capture a video of test execution for debugging?
 * 
 * Playwright supports built-in video recording using its recordVideo option in
 * the test configuration.
 * 📌 Options:
 * 
 * 'on' – record every test
 * 'on-first-retry' – record only if the test is retried
 * 'retain-on-failure' – record and keep video only if test fails
 * 'off' – disable video
 */
// playwright.config.js
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  use: {
    video: 'on-first-retry', // or 'on', 'retain-on-failure', 'off'
  },
});

// ✅ 4️⃣ Record Video Programmatically (Advanced Use)
// You can also create a context with video recording manually:

const context = await browser.newContext({
  recordVideo: { dir: 'videos/', size: { width: 1280, height: 720 } },
});
const page = await context.newPage();


/*
 * 4️⃣ What’s the Difference Between Headless and Headful Modes in Playwright?
 * 
 * 📌 Question: What is the purpose of headless mode and when should you run
 * tests in headful?
 * 
 * ✅ Answer:
 * headless: true → browser runs in the background (CI-friendly, faster)
 * headless: false → browser UI is visible (used for debugging)
 * await chromium.launch({ headless: false })
 */

/*
 * 5️⃣ How to Assert Element State (Enabled/Disabled/Editable) in Playwright?
 * 📌 Question: How can you verify if a button or field is disabled?
 */
await expect(page.locator('button#submit')).toBeEnabled();
await expect(page.locator('button#submit')).toBeDisabled();
await expect(page.locator('input[name="email"]')).toBeEditable();
await expect(page.locator('button#submit')).not.toBeEditable();

// DAY 51

/*
* 1️⃣ How Do You Handle Timeout for Actions or Assertions in Playwright?
* 📌 Question: How can you override the default timeout for a specific action
* or test?
* 
* You can override the default timeout for:
* A specific action
* A single assertion
* An entire test
* Playwright gives you fine-grained control over all of these.
*/
// ✅ A. Set Timeout for a Specific Action
await page.click('#checkout-button', { timeout: 7000 }); // waits up to 7 seconds

// ✅ B. Set Timeout for a Specific Assertion
await expect(page.locator('text=Thank you!')).toBeVisible({ timeout: 5000 });

// ✅ C. Set Timeout for an Entire Test
test('slow test with custom timeout', async ({ page }) => {
  test.setTimeout(20000); // 20 seconds for this test
  await page.goto('https://example.com/slow-page');
});

// ✅ D. Set Global Default Timeout in playwright.config.js
// playwright.config.js
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  timeout: 30000, // 30s max for each test
  use: {
    actionTimeout: 10000, // 10s max per action (click, fill, etc.)
  },
});


/*
 * 2️⃣ How to Configure Retries for All Tests in Playwright Config?
 * 📌 Question: How do you set up global retry rules in Playwright?
 * 
 * // playwright.config.js
 * const { defineConfig } = require('@playwright/test');
 * 
 * module.exports = defineConfig({
 * retries: 2, // Retry each failing test up to 2 times
 * retries: process.env.CI ? 2 : 0,
 * });
 * 
 */

/*
 * 3️⃣ What Is Playwright Trace Viewer and How Do You Use It?
 * 📌 Question: What is Playwright Trace Viewer, and how can it help debug test
 * failures?
 * 
 * It’s a web-based UI that lets you replay a recorded trace of your Playwright
 * test.
 * The trace includes:
 * Screenshots for each action
 * DOM snapshots
 * Network requests & responses
 * Console logs & errors
 * It’s like a time machine for your test runs.
 * 
 * module.exports = {
 * use: {
 * trace: 'on', // options: 'off', 'on', 'retain-on-failure', 'on-first-retry'
 * },
 * };
 * 
 * await context.tracing.start({ screenshots: true, snapshots: true });
 * 
 * await page.goto('https://example.com');
 * await page.click('button#submit');
 * 
 * await context.tracing.stop({ path: 'trace.zip' });
 * 
 * To Open the Trace Viewer
 * npx playwright show-trace trace.zip
 * 
 * ✅ What Can You Do in Trace Viewer?
 * Replay each step: See exactly what happened at every action.
 * Inspect the DOM: Check the state of the page at any moment.
 * View screenshots: Confirm visual state.
 * Check network requests: See API calls and responses.
 * View console logs and errors: Debug JS errors.
 * Jump between steps: Quickly identify where the test failed or hung.
 */

/*
 * 4️⃣ How to Work With Iframes in Playwright?
 * 📌 Question: How do you interact with elements inside an iframe?
 * 
 * Since iframes are essentially separate nested browsing contexts, you can’t
 * directly use page.locator() for elements inside an iframe. Instead, you:
 */
//  Get the iframe element handle
const iframeElementHandle = await page.$('iframe#my-frame');
// Get the iframe’s Frame object
const frame = await iframeElementHandle.contentFrame();
// Interact within that Frame
await frame.click('button#inside-iframe');
await frame.fill('input#name', 'Playwright');

// You can get all frames on the page with:
const frames = page.frames();


/*
 * 5️⃣ How to Handle Multiple Tabs or Popups in Playwright?
 * 📌 Question: How do you work with multiple pages or new tabs in Playwright?
 * 
 * When your app opens a new tab or popup, Playwright lets you:
 * Wait for the new page
 * Interact with it
 * Switch between pages easily
 * 
 * ✅ Open Tab vs. Popup — What's the Difference?
 * Both are Page objects in Playwright.
 * Tabs are often opened with <a target="_blank">
 * Popups are opened via window.open() in JavaScript
 */
// ✅ How to Work with Multiple Pages
const pages = context.pages(); // array of all open pages (tabs)
console.log(`Total open tabs: ${pages.length}`);

const secondTab = pages[1];
await secondTab.bringToFront(); // optional if tab switching matters


// Handling Login in a New Popup
const [popup] = await Promise.all([
  context.waitForEvent('page'),
  page.click('button.login-with-google'),
]);

await popup.fill('input[type="email"]', 'test@example.com');
await popup.click('button[type="submit"]');

// DAY 52

/*
 * 1️⃣ How Do You Persist Login Across Tests Using Storage State in Playwright?
 * 📌 Question: How do you reuse login sessions between tests without logging in
 * again?
 * 
 * You use storageState to save cookies and localStorage, then load it in other
 * tests or even test suites.
 */
const { chromium } = require('@playwright/test');

// ✅ 1. Create a Login Script That Saves Storage
(async () => {
  const browser = await chromium.launch();
  const context = await browser.newContext();
  const page = await context.newPage();

  await page.goto('https://your-app.com/login');
  await page.fill('#username', 'yourUser');
  await page.fill('#password', 'yourPassword');
  await page.click('button[type="submit"]');

  // Wait for navigation or confirmation element
  await page.waitForURL('**/dashboard');

  // Save the session (cookies, localStorage, etc.)
  await context.storageState({ path: 'auth.json' });

  await browser.close();
})();


// ✅ 2. Use the Stored Login in Your Tests
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  use: {
    storageState: 'auth.json', // load saved session here
  },
});


// ✅ 3. Optional: Use in a Single Test
test.use({ storageState: 'auth.json' });

test('should access dashboard as logged-in user', async ({ page }) => {
  await page.goto('https://your-app.com/dashboard');
  await expect(page.locator('text=Welcome')).toBeVisible();
});

/*
 * 2️⃣ How Can You Access or Validate Browser Cookies in Playwright?
 * 📌 Question: How do you get or validate cookies during a Playwright test?
 * 
 * You use the context.cookies() API to retrieve cookies and then inspect or
 * assert their values.
 */
(async () => {
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  // ✅ 1. Get All Cookies for Current Context
  let cookies = await context.cookies();
  console.log(cookies);

  // ✅ 2. Get Cookies for a Specific URL
  cookies = await context.cookies('https://your-app.com');

  // ✅ 3. Validate a Cookie in a Test
  test('should have session_id cookie', async ({ context, page }) => {
    await page.goto('https://your-app.com/dashboard');
    const cookies = await context.cookies();
    const sessionCookie = cookies.find(c => c.name === 'session_id');

    expect(sessionCookie).toBeTruthy();
    expect(sessionCookie.value).toMatch(/^[a-z0-9]+$/);
  });

  // ✅ 4. Set a Cookie Manually (Optional)
  await context.addCookies([
    {
      name: 'session_id',
      value: 'mocked-value',
      url: 'https://your-app.com',
      path: '/',
      httpOnly: true,
      secure: true
    }
  ]);

  //  ✅ 5. Clear Cookies
  await context.clearCookies();

});

/*
 * 3️⃣ How Can You Send Direct API Requests in Playwright Tests?
 * 📌 Question: How do you make a direct backend API call within your UI test?
 * 
 * You use the request fixture (or APIRequestContext) to make HTTP calls like
 * GET, POST, PUT, etc. directly within your tests or setup steps.
 */
test('API call to get user details', async ({ request }) => {
  // ✅ Option 1: Use request Fixture in Your Tests
  const response = await request.get('https://api.example.com/users/1');
  expect(response.ok()).toBeTruthy();

  const data = await response.json();
  expect(data.name).toBe('John Doe');


  // ✅ Option 2: Create a Custom APIRequestContext
  const requestContext = await request.newContext({
    baseURL: 'https://api.example.com',
    extraHTTPHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  const response2 = await requestContext.get('/me');
  const user = await response2.json();

});

/*
 * 4️⃣ What Is expect.poll() and When Should You Use It?
📌 Question: What is expect.poll() and how is it useful for dynamic assertions?

expect.poll() lets you:
Continuously evaluate a custom function
Wait until the result matches your expectation
Retry automatically until a timeout or success

It’s super useful when you’re asserting on:
Values fetched from the page (like counters or API values)
Computed values or state outside the DOM
Console logs or metrics
 */

//  ✅ Example 1: Wait for a JavaScript Variable to Update
await page.evaluate(() => window.counter = 0);

// Simulate async update
await page.evaluate(() => {
  setTimeout(() => window.counter = 5, 3000);
});

// Wait until the counter becomes 5
await expect.poll(() => page.evaluate(() => window.counter)).toBe(5);

// ✅ Example 2: Wait for API Value or Computed Text
await expect.poll(async () => {
  const response = await fetch('/api/status');
  const data = await response.json();
  return data.status;
}).toBe('READY');

// ✅ Example 3: Polling Text Value of an Element (alternative to toHaveText)
const locator = page.locator('#status');
await expect.poll(async () => await locator.textContent()).toContain('Success');

//  ✅ Custom Timeout or Interval
await expect.poll(() => getValue(), {
  timeout: 5000,   // default is 5s
  interval: 200    // default is 100ms
}).toBe('done');


/*
 * 5️⃣ How to Emulate a Mobile Device in Playwright?
 * 📌 Question: How do you simulate a mobile device view for testing responsive
 * apps?
 * 
 * 
 * Playwright supports mobile emulation via:
 * Built-in device descriptors (like iPhone 12)
 * Custom viewport, user agent, and touch settings
 */
// ✅ Option 1: Use a Built-in Mobile Device Descriptor
//✅ Includes: Mobile viewport; iPhone user agent; Touch support; Device scale factor
const { test, devices } = require('@playwright/test');

test.use({
  ...devices['iPhone 12'], // emulates viewport, user agent, touch, etc.
});

test('mobile view test', async ({ page }) => {
  await page.goto('https://example.com');
  await page.screenshot({ path: 'iphone-view.png' });
});


// ✅ Option 2: Emulate Mobile Manually (Custom Settings)
const context1 = await browser.newContext({
  viewport: { width: 375, height: 812 },
  userAgent: 'Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)...',
  isMobile: true,
  hasTouch: true,
  deviceScaleFactor: 3,
});

const page1 = await context1.newPage();
await page1.goto('https://example.com');

// ✅ View Available Devices
const { devices } = require('@playwright/test');
console.log(Object.keys(devices)); // Lists all supported devices


// DAY 53
/*
 * 1️⃣ How to Handle File Downloads in Playwright?
📌 Question: How do you verify a file was downloaded using Playwright?

You can detect a file download like this:
Wait for the download event
Trigger the download action
Save the file (optional)
Assert its filename or contents (optional)
 */
test('should download a file', async ({ page }) => {
  // 1. Wait for the download to start
  const [download] = await Promise.all([
    page.waitForEvent('download'), // wait for the download
    page.click('text=Download Report'), // action that triggers download
  ]);

  // 2. Save the file to a specific path
  const path = await download.path(); // temporary path
  console.log('Downloaded to:', path);

  await download.saveAs('downloads/report.csv'); // save to custom folder

  // 3. Validate filename
  expect(download.suggestedFilename()).toBe('report.csv');
});

// ✅ Optional: Read File Content (Node.js)
const fs = require('fs');

const contents = fs.readFileSync('downloads/report.csv', 'utf-8');
expect(contents).toContain('User ID, Name');



// ✅ Configure Download Path (Globally)
const context3 = await browser.newContext({
  acceptDownloads: true,
  downloadsPath: 'downloads/',
});


 /*
  * 2️⃣ What Happens If You Upload a File That Overwrites an Existing One?
📌 Question: Can you simulate file overwrite scenarios in Playwright during uploads?

✅ Answer:
 Yes — you can re-upload or upload a file with the same name:
  */
await page.setInputFiles('input[type="file"]', 'files/report.pdf');
await page.setInputFiles('input[type="file"]', 'files/report.pdf'); // overwrite simulation

  /*
   * 3️⃣ How Can You Assert Browser Console Errors in Playwright?
📌 Question: How do you capture and validate JS console errors during a test run?
   */
test('should not log any console errors', async ({ page }) => {
  const errors = [];

  // Listen for console messages of type 'error'
  page.on('console', (msg) => {
    if (msg.type() === 'error') {
      errors.push(msg.text());
    }
    // specific error
    let found = false;
     if (msg.type() === 'error' && msg.text().includes('API key missing')) {
      found = true;
    }
  });

  await page.goto('https://your-app.com');

  // Perform some actions...
  await page.click('text=Start');

  // Assert no errors occurred
  expect(errors).toEqual([]);
  expect(found).toBe(true);

  // ✅ Capture All Console Messages (for debugging)
  page.on('console', (msg) => {
  console.log(`[${msg.type()}] ${msg.text()}`);
});

});

   /*
    * 4️⃣ How Do You Modify Request Headers in Playwright?
📌 Question: How do you inject custom headers like auth tokens or locale info into a request?
    */
const context4 = await browser.newContext({
 extraHTTPHeaders: {
 'Authorization': 'Bearer test-token',
 'X-Custom-Header': 'PlaywrightTest'
 }
});


/*
 * 5️⃣ How Can You Use Environment-Specific Configs with --project in
 * Playwright?
 * 📌 Question: How can you test across environments like staging, QA, and prod
 * using Playwright config?
 * 
 * n Playwright, you can easily manage environment-specific configurations (e.g.
 * staging, qa, production) using the --project flag along with multiple project
 * definitions in your playwright.config.ts (or .js) file.
 * This approach lets you:
 * Define different base URLs, credentials, or settings per environment
 * Run tests across environments using --project flag
 * Keep all test logic in one place while toggling config dynamically
 */
// ✅ 1. Define Multiple Projects in playwright.config.ts
import { defineConfig } from '@playwright/test';

export default defineConfig({
  projects: [
    {
      name: 'staging',
      use: {
        baseURL: 'https://staging.example.com',
        storageState: 'storage/staging.json',
      },
    },
    {
      name: 'qa',
      use: {
        baseURL: 'https://qa.example.com',
        storageState: 'storage/qa.json',
      },
    },
    {
      name: 'prod',
      use: {
        baseURL: 'https://example.com',
        storageState: 'storage/prod.json',
      },
    },
  ],
});

// ✅ 2. Use --project Flag to Run a Specific Environment
// npx playwright test --project=staging
// npx playwright test tests/login.spec.ts --project=qa


// ✅ 3. Access Config in Your Tests
test('check homepage', async ({ page }) => {
  await page.goto('/'); // You can access environment-specific values like baseURL via the page.goto() method:
  await expect(page.locator('h1')).toContainText('Welcome');
});

// ✅ Optional: Use process.env for CI-driven config
const ENV = process.env.TEST_ENV || 'staging';

const baseURLs = {
  staging: 'https://staging.example.com',
  qa: 'https://qa.example.com',
  prod: 'https://example.com',
};

// export default defineConfig({
//   use: {
//     baseURL: baseURLs[ENV],
//   },
// });

// TEST_ENV=qa npx playwright test