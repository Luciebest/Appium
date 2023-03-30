package com.cegeka.academy.qa.configurations;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Configuration
@ComponentScan(basePackages = "com.cegeka.academy.qa")
@PropertySource("classpath:framework.properties")
public class FrameworkConfiguration {

    private AndroidDriver driver;

    @Bean
    public AndroidDriver getAndroidDriver() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Maria\\chromedrivers\\chromedriver111.exe");
        try {
           driver= new AndroidDriver(new URL("http://127.0.0.1:4445/"), getDesiredCapabilities());
        } catch (Exception exception) {
            throw new RuntimeException("Android driver could not be started", exception);
        }

        return driver;
    }
    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities =  new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidDevice");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 2000);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, "true");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\Maria\\apks\\SauceLabDemo.apk");

        return desiredCapabilities;
    }

    @PreDestroy
    public void destroy() {
        if (driver != null)  {
            driver.terminateApp("com.saucelabs.mydemoapp.rn");
            driver.quit();
            System.out.println("Driver quit was called");
        }
        else System.out.println("Cannot quit driver because it's already null");
    }

}
