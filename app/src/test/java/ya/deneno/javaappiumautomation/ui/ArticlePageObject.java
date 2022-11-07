package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {
    public static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//*[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            ADD_TO_MY_LIST_AFTER_ON_BOARDING = "//*[@resource-id ='org.wikipedia:id/item_container']//*[@text='{FOLDER_NAME}']",
            MY_LIST_MAIN_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//*[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return waitForElementPresent(
                By.id(TITLE),
                "Cannot find Article title on page",
                15
        );
    }

    public String getArticleTitle() {
        return waitForTitleElement().getAttribute("text");
    }

    public void swipeToFooter() {
        swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Can't find the end of the article",
                20
        );
    }

    public void addArticleToMyList(String nameOfFolder) {
        waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );
        waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );
        waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' button",
                5
        );
        waitForElementAndClear(
                By.id(MY_LIST_MAIN_INPUT),
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndSendKeys(
                By.id(MY_LIST_MAIN_INPUT),
                nameOfFolder,
                "Cannot find 'text_input' field",
                5
        );
        waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find 'OK' button",
                5
        );
    }

    public void addArticleToMyListAfterOnBoarding(String nameOfFolder) {
        waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );
        waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );
        waitForElementAndClick(
                By.xpath(getFolderNameElement(nameOfFolder)),
                "Cannot find list with name '" + nameOfFolder + "'",
                5
        );
    }

    public void closeArticle() {
        waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find 'X' button",
                5
        );
    }

    private static String getFolderNameElement(String nameOfFolder) {
        return ADD_TO_MY_LIST_AFTER_ON_BOARDING.replace("{FOLDER_NAME}", nameOfFolder);
    }
}
