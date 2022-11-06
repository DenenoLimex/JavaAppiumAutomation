package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {
    public static final String SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";
    private static final String
            SEARCH_INIT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id ='org.wikipedia:id/search_results_list']//*[@resource-id ='org.wikipedia:id/page_list_item_container']";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        waitForElementAndClick(
                By.xpath(SEARCH_INIT),
                "Cannot find and click search init element",
                5
        );
        waitForElementPresent(
                By.xpath(SEARCH_INIT),
                "Cannot find and click search init element"
        );
    }

    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                searchLine,
                "Cannot find and type into search input",
                5
        );
    }

    public void waitForSearchResult(String substring) {
        waitForElementPresent(
                By.xpath(getResultSearchElement(substring)),
                "Cannot find search result with substring " + substring
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        waitForElementAndClick(
                By.xpath(getResultSearchElement(substring)),
                "Cannot find and click search result with substring " + substring,
                10
        );
    }

    public void waitForCancelButtonToAppear() {
        waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button"
        );
    }

    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present",
                5
        );
    }

    public void clickCancelSearch() {
        waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5
        );
    }

    public int getAmountOfFoundArticles() {
        waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by request",
                15
        );
        return getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultLabel(){
        waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15
        );
    }


    public void assertThereIsNoResultOfSearch() {
        assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results"
        );
    }

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
}