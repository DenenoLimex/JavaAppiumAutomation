package ya.deneno.javaappiumautomation;

import junit.framework.TestCase;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumUrl = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Users/Deneno/Desktop/JavaAppiumAutomation/app/apks/org.wikipedia.apk");
        driver = new AndroidDriver(new URL(appiumUrl), capabilities);
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }
}
