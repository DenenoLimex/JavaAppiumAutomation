package ya.deneno.javaappiumautomation.ui;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public void assertElementPresent(String errorMessage, By by) {
        boolean isElementPresentAtOnce;
        try {
            isElementPresentAtOnce = driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            isElementPresentAtOnce = false;
        }
        Assert.assertTrue(
                errorMessage,
                isElementPresentAtOnce
        );
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        driver.hideKeyboard();
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String value, String errorMessage) {
        Assert.assertEquals(
                "ElementText not equals expected value",
                waitForElementPresent(by, errorMessage).getAttribute("text"),
                value
        );
    }

    public List<WebElement> waitForAllElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipe) {
        int already_swipe = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swipe > maxSwipe) {
                waitForElementPresent(by, "Can't find element by swiping up\n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            already_swipe++;
        }
    }

    public void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 15);
        int leftX = element.getLocation().x;
        int rightX = leftX + element.getSize().width;
        int upperY = element.getLocation().y;
        int lowerY = upperY + element.getSize().height;
        int middleY = (upperY + lowerY) / 2;
        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();
    }

    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
