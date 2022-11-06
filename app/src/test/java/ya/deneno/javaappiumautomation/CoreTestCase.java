package ya.deneno.javaappiumautomation;

import junit.framework.TestCase;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import ya.deneno.javaappiumautomation.ui.ArticlePageObject;
import ya.deneno.javaappiumautomation.ui.MyListPageObject;
import ya.deneno.javaappiumautomation.ui.NavigationUiObject;
import ya.deneno.javaappiumautomation.ui.SearchPageObject;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumUrl = "http://127.0.0.1:4723/wd/hub";
    protected SearchPageObject searchPageObject;
    protected ArticlePageObject articlePageObject;
    protected NavigationUiObject navigationUiObject;
    protected MyListPageObject myListPageObject;

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
        searchPageObject = new SearchPageObject(driver);
        articlePageObject = new ArticlePageObject(driver);
        navigationUiObject = new NavigationUiObject(driver);
        myListPageObject = new MyListPageObject(driver);
        rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(seconds);
    }
}
