package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class MyListPageObject extends MainPageObject {
    public static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        waitForElementAndClick(
                By.xpath(getFolderXpathByName(nameOfFolder)),
                "Cannot find folder with name " + nameOfFolder,
                5
        );

    }

    public void swipeByArticleToDelete(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForArticleToAppearByTitle(articleTitleXpath);
        swipeElementToLeft(
                By.xpath(articleTitleXpath),
                "Cannot find saved article"
        );
        waitForArticleToDisappearByTitle(articleTitleXpath);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        waitForElementNotPresent(
                By.xpath(getSavedArticleXpathByTitle(articleTitle)),
                "Cannot find saved article with title " + articleTitle,
                5
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        waitForElementNotPresent(
                By.xpath(getSavedArticleXpathByTitle(articleTitle)),
                "Saved article still present with title " + articleTitle,
                5
        );
    }

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }
}
