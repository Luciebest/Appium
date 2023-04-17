package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.pageobjects.ProductsPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

public class SauceLabsNativeSteps {

    @Autowired
    private AndroidDriver driver;


    @Given("app state is running in foreground")
    public void appStateIsRunningInForeground() {
        driver.queryAppState("com.saucelabs.mydemoapp.rn").equals(ApplicationState.RUNNING_IN_FOREGROUND);
        driver.getPageSource();
        driver.getContextHandles();
    }

    @When("User opens native menu")
    public void iOpenNativeMenu() {

//        driver.findElement();
        getFluentWait()
                .until(ExpectedConditions.elementToBeClickable(AppiumBy.ByAccessibilityId.accessibilityId("open menu")))
                .click();

    }

    @Then("User verifies native menu is opened")
    public void userVerifiesNativeMenuIsOpened() {

        boolean isMenuDisplayed = getFluentWait().until(ExpectedConditions.presenceOfElementLocated(AppiumBy.ByAccessibilityId.accessibilityId("menu item log in"))).isDisplayed();
        Assert.assertTrue("Native menu was not opened", isMenuDisplayed);
    }

    FluentWait<AndroidDriver> getFluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
    }

    @When("User selects login from native menu")
    public void userSelectsLoginFromNativeMenu() {
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("menu item log in")).click();
    }

    @Then("Login native screen is displayed")
    public void loginNativeScreenIsDisplayed() {
        boolean isLoginScreenDisplayed = getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath("//*[@content-desc='login screen']"))).isDisplayed();
//        boolean isLoginScreenDisplayed = driver.findElement(By.xpath("//*[@content-desc='login screen']")).isDisplayed();
        Assert.assertTrue("Login screen is not displayed", isLoginScreenDisplayed);
    }

    @When("Context is changed to webview")
    public void iChangeContextToWebview() {
        System.out.println("All possible available contexts :" + driver.getContextHandles());
        System.out.println("Current context is:" + driver.getContext());
        System.out.println("Trying to change context to webview");
        driver.getContextHandles()
                .stream()
                .filter(context -> context.equals("WEBVIEW_com.saucelabs.mydemoapp.rn"))
                .findFirst()
                .ifPresent(webViewContext -> driver.context(webViewContext));
        System.out.println("Current context is:" + driver.getContext());
    }

    @When("User logs in with username {string} and password {string} on the native login screen")
    public void userLogsInWithUsernameAndPasswordOnTheNativeLoginScreen(String username, String password) {
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("Username input field")).sendKeys(username);
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("Password input field")).sendKeys(password);
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("Login button")).click();
    }

    @Then("User will not see login screen")
    public void userWillNotSeeLoginScreen() {
        try {
            boolean isLoginScreenAbsent = getFluentWait()
                    .withTimeout(Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.ByXPath.xpath("//*[@content-desc='login screen']")));
            Assert.assertTrue("Login screen is still displayed", isLoginScreenAbsent);
        } catch (TimeoutException e) {
            Assert.fail("Timeout waiting for login screen to disappear");
        }
    }
    @When("User selects Webview from native menu")
    public void userSelectsWebviewFromNativeMenu() {
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("menu item webview")).click();
    }
    @Then("Webview selection is displayed")
    public void webviewSelectionIsDisplayed() {
        boolean isWebviewSelected = getFluentWait()
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.ByAccessibilityId.accessibilityId("webview selection screen")))
                .isDisplayed();

        Assert.assertTrue("Webview selection is not displayed", isWebviewSelected);
    }
    @When("User enters {string} in webview selection and clicks enter")
    public void userEntersURLInWebviewSelection(String url) {
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("URL input field")).sendKeys(url);
        driver.findElement(AppiumBy.ByAccessibilityId.accessibilityId("Go To Site button")).click();
    }

    @When("User logs in with username {string} and password {string} on the webview login screen")
    public void userLogsInWithUsernameAndPasswordOnTheWebviewLoginScreen(String username, String password) {
        WebElement usernameField = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("[data-test='password']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
        loginButton.click();
    }

    @Then("User is redirected to products page in webview")
    public void UserIsRedirectedToProductsPageInWebview() {
        WebElement inventoryContainer = driver.findElement(By.id("inventory_container"));
        Assert.assertTrue("Products are not displayed",inventoryContainer.isDisplayed());
        driver.quit();

    }

}
