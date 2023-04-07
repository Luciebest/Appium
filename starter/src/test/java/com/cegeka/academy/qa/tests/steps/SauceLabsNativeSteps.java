package com.cegeka.academy.qa.tests.steps;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

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
        FluentWait<AndroidDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);

        return fluentWait;

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
    public void userLogsInWithUsernameAndPasswordOnTheNativeLoginScreen(String arg0, String arg1) {
        //TODO homework. Delete exception after implementing step.
        throw new RuntimeException("Implement this step. This is the homework");
    }

    @Then("User will not see login screen")
    public void userWillNotSeeLoginScreen() {
        //TODO homework. Delete exception after implementing step.
        throw new RuntimeException("Implement this step. This is the homework");
    }

}
