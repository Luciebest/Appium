package com.cegeka.academy.qa.tests.steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class CucumberHooks {

    @Autowired
    private AppiumDriver driver;

    @Before
    public void setupBeforeScenario() {
        System.out.println("Perform before scenario cucumber hook");
        System.out.println("Driver instance " +  driver);
        System.out.println("Perform before scenario cucumber hook");
//      clear app data on the device

//      old way to clear app
        if (driver instanceof AndroidDriver) ((AndroidDriver) driver).resetApp();

//        new way to clear app
//        driver.executeScript("mobile:clearApp", Map.of("appId", "com.saucelabs.mydemoapp.rn"));
//        if (driver instanceof AndroidDriver) ((AndroidDriver) driver).activateApp("com.saucelabs.mydemoapp.rn");
    }

}
