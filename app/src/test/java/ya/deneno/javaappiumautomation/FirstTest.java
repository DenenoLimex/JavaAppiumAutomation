package ya.deneno.javaappiumautomation;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import io.appium.java_client.TouchAction;

public class FirstTest extends CoreTestCase {

    @Test
    public void testSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_cab_view"),
                "Cannot find search field",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Search close button' input",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Search close button is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title input",
                15
        );
        String titleArticle = titleElement.getAttribute("text");
        Assert.assertEquals("Article title not equals", titleArticle, "Java (programming language)");
    }

    @Test
    public void testCheckTextForElementSearchField() {
        assertElementHasText(
                By.xpath("//*[@resource-id ='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Search Wikipedia",
                "Search field text not equals 'Search Wikipedia'"
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search field text not equals 'Search…'"
        );
    }

    @Test
    public void testCheckCountOfElementsBySearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        Assert.assertTrue(
                "Count of element < 2",
                waitForAllElementsPresent(
                        By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']"),
                        "Cannot find result of search",
                        15
                ).size() > 1
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_cab_view"),
                "Cannot find search field",
                5
        );
        waitForElementNotPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']"),
                "Result of search is still present on the page",
                5
        );
    }

    @Test
    public void testCheckTextElementsBySearch() {
        String value = "Java";
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                value,
                "Cannot find 'Search…' input",
                5
        );
        List<WebElement> elements = waitForAllElementsPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_title']"),
                "Cannot find result of search",
                5
        );
        for (WebElement element : elements) {
            Assert.assertTrue(
                    "Some of search result not contains '" + value + "'",
                    element.getAttribute("text").contains(value)
            );
        }
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find search 'Appium' topic searching by 'Appium'",
                5
        );
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can't find the end of the article",
                20
        );
    }

    @Test
    public void testSaveFirstArticleForMyList() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title input",
                15
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Learning programming",
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find list with title 'Learning programming'",
                5
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find list item with title 'Java (programming language)'"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete save list item with title 'Java (programming language)'",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search = "Linkin Park Discography";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        String xpath = "//*[@resource-id ='org.wikipedia:id/search_results_list']//*[@resource-id ='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(xpath),
                "Cannot find 'page_list_item_container' input for value = " + search,
                15
        );
        Assert.assertTrue(
                "Count of element is 0",
                getAmountOfElements(By.xpath(xpath)) > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        String search = "zxvzxvzxcvzxcvzxcv";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@text='No results found']"),
                "Cannot find 'No results found'",
                15
        );
        String xpath = "//*[@resource-id ='org.wikipedia:id/search_results_list']//*[@resource-id ='org.wikipedia:id/page_list_item_container']";
        assertElementNotPresent(
                By.xpath(xpath),
                "Count of element with request '" + search + "' > 0"
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String search = "Java";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by '" + search + "'",
                5
        );
        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find Article title input",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find Article title input",
                15
        );
        Assert.assertEquals(
                "Article title have benn changed after rotation",
                titleAfterRotation,
                titleBeforeRotation
        );
        driver.rotate(ScreenOrientation.PORTRAIT);
        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find Article title input",
                15
        );
        Assert.assertEquals(
                "Article title have benn changed after rotation",
                titleAfterRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        String search = "Java";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        driver.runAppInBackground(2);
        waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search 'Object-oriented programming language' topic searching by 'Java' after returning background",
                5
        );
    }

    @Test
    public void testSaveTwoArticleForMyListAndDeleteOne() {
        String searchFirst = "Java (programming language)";
        String searchSecond = "Kotlin (programming language)";
        String listName = "Learning programming";
        searchArticleAndOpenViewSaveToList(searchFirst);
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                listName,
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        searchArticleAndOpenViewSaveToList(searchSecond);
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
                "Cannot find list with name '" + listName + "'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + listName + "']"),
                "Cannot find list with title '" + listName + "'",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + searchFirst + "']"),
                "Cannot find list item with title '" + searchFirst + "'"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='" + searchFirst + "']"),
                "Cannot delete save list item with title '" + searchFirst + "'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + searchSecond + "']"),
                "Cannot delete save list item with title '" + searchSecond + "'",
                5
        );
        String titleAfterDeleteFirstSavedArticle = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find Article title input",
                15
        );
        Assert.assertEquals(
                "Article title have been changed after delete first saved article",
                searchSecond,
                titleAfterDeleteFirstSavedArticle
        );
    }

    private void searchArticleAndOpenViewSaveToList(String searchFirst) {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchFirst,
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='" + searchFirst + "']"),
                "Cannot find '" + searchFirst + "' by search result",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title input",
                15
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );
    }

    @Test
    public void testArticleTitlePresentAtOnce() {
        String search = "Java (programming language)";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='" + search + "']"),
                "Cannot find '" + search + "' by search result",
                5
        );
        assertElementPresent(
                "Cannot find title with text '" + search + "' at once",
                By.xpath("//*[@text='" + search + "']")
        );
    }

    private void assertElementPresent(String errorMessage, By by) {
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

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        driver.hideKeyboard();
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String value, String errorMessage) {
        Assert.assertEquals(
                "ElementText not equals expected value",
                waitForElementPresent(by, errorMessage).getAttribute("text"),
                value
        );
    }

    private List<WebElement> waitForAllElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        sleep();
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipe) {
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

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 15);
        int leftX = element.getLocation().x;
        int rightX = leftX + element.getSize().width;
        int upperY = element.getLocation().y;
        int lowerY = upperY + element.getSize().height;
        int middleY = (upperY + lowerY) / 2;
        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}