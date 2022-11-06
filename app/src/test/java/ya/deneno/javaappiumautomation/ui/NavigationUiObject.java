package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class NavigationUiObject extends MainPageObject {
    public static final String MY_LISTS_LINK = "//*[@content-desc='My lists']";

    public NavigationUiObject(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyList() {

        waitForElementAndClick(
                By.xpath(MY_LISTS_LINK),
                "Cannot find 'My lists' button",
                5
        );
    }
}
