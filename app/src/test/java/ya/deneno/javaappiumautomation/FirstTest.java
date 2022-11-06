package ya.deneno.javaappiumautomation;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import ya.deneno.javaappiumautomation.ui.ArticlePageObject;
import ya.deneno.javaappiumautomation.ui.MainPageObject;
import ya.deneno.javaappiumautomation.ui.MyListPageObject;
import ya.deneno.javaappiumautomation.ui.NavigationUiObject;
import ya.deneno.javaappiumautomation.ui.SearchPageObject;

public class FirstTest extends CoreTestCase {
    private MainPageObject mainPageObject;
    private SearchPageObject searchPageObject;
    private ArticlePageObject articlePageObject;
    private NavigationUiObject navigationUiObject;
    private MyListPageObject myListPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
        searchPageObject = new SearchPageObject(driver);
        articlePageObject = new ArticlePageObject(driver);
        navigationUiObject = new NavigationUiObject(driver);
        myListPageObject = new MyListPageObject(driver);
    }

    @Test
    public void testSearch() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String articleTitle = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "We see unexpected title!",
                articleTitle,
                "Java (programming language)"
        );
    }

    @Test
    public void testSwipeArticle() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testCheckTextForElementSearchField() {
        mainPageObject.assertElementHasText(
                By.xpath("//*[@resource-id ='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Search Wikipedia",
                "Search field text not equals 'Search Wikipedia'"
        );
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        mainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search field text not equals 'Search…'"
        );
    }

    @Test
    public void testCheckCountOfElementsBySearch() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' input",
                5
        );
        Assert.assertTrue(
                "Count of element < 2",
                mainPageObject.waitForAllElementsPresent(
                        By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']"),
                        "Cannot find result of search",
                        15
                ).size() > 1
        );
        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_cab_view"),
                "Cannot find search field",
                5
        );
        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']"),
                "Result of search is still present on the page",
                5
        );
    }

    @Test
    public void testCheckTextElementsBySearch() {
        String value = "Java";
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                value,
                "Cannot find 'Search…' input",
                5
        );
        List<WebElement> elements = mainPageObject.waitForAllElementsPresent(
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
    public void testSaveFirstArticleForMyList() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();
        navigationUiObject.clickMyList();
        myListPageObject.openFolderByName(nameOfFolder);
        myListPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        searchPageObject.initSearchInput();
        String search = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search);
        searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "Count of element is 0",
                searchPageObject.getAmountOfFoundArticles() > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        searchPageObject.initSearchInput();
        String search = "zxvzxvzxcvzxcvzxcv";
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleAfterRotation,
                titleBeforeRotation
        );
        rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleAfterRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testSaveTwoArticleForMyListAndDeleteOne() {
        String searchFirst = "Java (programming language)";
        String searchSecond = "Kotlin (programming language)";
        String listName = "Learning programming";
        searchArticleAndOpenViewSaveToList(searchFirst);
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );
        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'text_input' field",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                listName,
                "Cannot find 'text_input' field",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        searchArticleAndOpenViewSaveToList(searchSecond);
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
                "Cannot find list with name '" + listName + "'",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + listName + "']"),
                "Cannot find list with title '" + listName + "'",
                5
        );

        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + searchFirst + "']"),
                "Cannot find list item with title '" + searchFirst + "'"
        );
        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + searchFirst + "']"),
                "Cannot delete save list item with title '" + searchFirst + "'",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + searchSecond + "']"),
                "Cannot delete save list item with title '" + searchSecond + "'",
                5
        );
        String titleAfterDeleteFirstSavedArticle = mainPageObject.waitForElementAndGetAttribute(
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
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchFirst,
                "Cannot find 'Search…' input",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='" + searchFirst + "']"),
                "Cannot find '" + searchFirst + "' by search result",
                5
        );
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find Article title input",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );
    }

    @Test
    public void testArticleTitlePresentAtOnce() {
        String search = "Java (programming language)";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search,
                "Cannot find 'Search…' input",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='" + search + "']"),
                "Cannot find '" + search + "' by search result",
                5
        );
        mainPageObject.assertElementPresent(
                "Cannot find title with text '" + search + "' at once",
                By.xpath("//*[@text='" + search + "']")
        );
    }
}