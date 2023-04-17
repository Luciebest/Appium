
package com.cegeka.academy.qa.configurations;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.AfterAll;
import org.junit.After;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;
import java.net.URL;

@Configuration
@ComponentScan(basePackages = "com.cegeka.academy.qa")
@PropertySource("classpath:framework.properties")
public class FrameworkConfiguration {

    private AndroidDriver driver;

    @Bean
    public AndroidDriver getAndroidDriver() {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lucia\\developer\\chromedrivers\\chromedriver101\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "E:\\chromedrivers\\chromedriver101\\chromedriver.exe");
        try {
            //for appium 1.x (appium server gui) the path is http://127.0.0.1:4445/wd/hub
            //for appium 2.0 beta (installed from node) the path is like this, without /wd/hub
           driver= new AndroidDriver(new URL("http://127.0.0.1:4445/"), getDesiredCapabilities());
        } catch (Exception exception) {
            throw new RuntimeException("Android driver could not be started", exception);
        }

        return driver;
    }
    private DesiredCapabilities getDesiredCapabilities() {
        //This is the old way to setup using Desired Capabilities which was announced to be deprecated in the future
        // I used it in order to work on both appium 1.x and 2.0
        //at the moment (appium 2.0.0-beta.58 and java-client 8.3.0) it works because the client translates them in w3c capabilities using the AndroidUiautomator2Driver in this case, and it puts "the appium:" in front of vendor specific capabilities.
//[debug] [AndroidUiautomator2Driver@197b] Creating session with W3C capabilities: {
//[debug] [AndroidUiautomator2Driver@197b]   "alwaysMatch": {
//[debug] [AndroidUiautomator2Driver@197b]     "platformName": "android",
//[debug] [AndroidUiautomator2Driver@197b]     "appium:app": "C:\\Users\\Maria\\apks\\SauceLabDemo.apk",
//[debug] [AndroidUiautomator2Driver@197b]     "appium:automationName": "UiAutomator2",
//[debug] [AndroidUiautomator2Driver@197b]     "appium:deviceName": "AndroidDevice",
//[debug] [AndroidUiautomator2Driver@197b]     "appium:newCommandTimeout": 2000,
//[debug] [AndroidUiautomator2Driver@197b]     "appium:noReset": "true",
//[debug] [AndroidUiautomator2Driver@197b]     "appium:platformVersion": "13"
//                        [debug] [AndroidUiautomator2Driver@197b]   },
//[debug] [AndroidUiautomator2Driver@197b]   "firstMatch": [
//[debug] [AndroidUiautomator2Driver@197b]     {}
//[debug] [AndroidUiautomator2Driver@197b]   ]
//[debug] [AndroidUiautomator2Driver@197b] }

        DesiredCapabilities desiredCapabilities =  new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidDevice");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 2000);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        //Update to you apk path
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\Lucia\\apk\\SauceLabDemo.apk");
        //Update to your chromedriver path. This must match the browser version(android webview version) on mobile device. It will fail to change context if not properly set
        desiredCapabilities.setCapability(UiAutomator2Options.CHROMEDRIVER_EXECUTABLE_OPTION, "E:\\chromedrivers\\chromedriver101\\chromedriver.exe");
//
        return desiredCapabilities;
//        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options()
//                .setPlatformName("Android")
//                .setPlatformVersion("13")
//                .setAutomationName("UiAutomator2")
//                .setDeviceName("AndroidDevice")
//                .setNewCommandTimeout(Duration.ofSeconds(2000))
//                .setNoReset(false)
//                .setApp("C:\\Users\\Maria\\apks\\SauceLabDemo.apk")
//                .setChromedriverExecutable("C:\\Users\\Maria\\chromedrivers\\chromedriver103.exe");
//
//        return uiAutomator2Options;

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
