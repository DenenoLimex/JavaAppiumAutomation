package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {
    protected AppiumDriver driver;
    private static final String
            SEARCH_INIT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";


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

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
}